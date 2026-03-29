package com.example.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.CreateOrderItemRequest;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.response.AdminOrderResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.PaymentEntity;
import com.example.demo.mapper.OrderMapperToAdmin;
import com.example.demo.mapper.OrderMapperToClient;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.dto.request.ClientCreateOrderRequest;
import com.example.demo.dto.response.ClientOrderResponse;
import com.example.demo.service.OrderService;
import com.example.demo.specification.OrderSpecification;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapperToClient mapper;
	private final OrderMapperToAdmin mapperAdmin;

    public OrderServiceImpl(
            OrderRepository repo,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository,
            OrderMapperToClient mapper,
            OrderMapperToAdmin mapperAdmin
    ) {
        this.repo = repo;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.mapperAdmin = mapperAdmin;
    }

    @Override
    public Page<AdminOrderResponse> search(
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Double minTotal,
            Double maxTotal,
            Long userId,
            Long employeeId,
            String orderSource,
            String status,
            Pageable pageable
    ) {

        Specification<OrderEntity> spec = Specification
                .where(OrderSpecification.fromDate(fromDate))
                .and(OrderSpecification.toDate(toDate))
                .and(OrderSpecification.minTotal(minTotal))
                .and(OrderSpecification.maxTotal(maxTotal))
                .and(OrderSpecification.userId(userId))
                .and(OrderSpecification.employeeId(employeeId))
                .and(OrderSpecification.orderSource(orderSource))
                .and(OrderSpecification.status(status));

        Page<OrderEntity> orders = repo.findAll(spec, pageable);

        return orders.map(mapperAdmin::toAdmin);
    }

    @Transactional
    @Override
    public AdminOrderResponse create(CreateOrderRequest request) {

        //  create order
        OrderEntity order = new OrderEntity();
        order.setOrderSource(request.getOrderSource());
        order.setTableId(request.getTableId());
        order.setUserId(request.getUserId());
        order.setEmployeeId(request.getEmployeeId());
        order.setStatus(request.getStatus());
        order.setTotalAmount(request.getTotalAmount());
        order.setMethodPayment(request.getMethodPayment());

        if (request.getCreated_at() != null) {
            order.setCreatedAt(
                    request.getCreated_at().toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        } else {
            order.setCreatedAt(LocalDateTime.now());
        }

        OrderEntity savedOrder = repo.save(order);
        if (request.getItems() != null) {
	        for (CreateOrderItemRequest item : request.getItems()) {
	
	            OrderItemEntity orderItem = new OrderItemEntity();
	            orderItem.setOrderId(savedOrder.getId());
	            orderItem.setProductId(item.getProductId());
	            orderItem.setQuantity(item.getQuantity());
	            orderItem.setPrice(item.getPrice());
	
	            orderItemRepository.save(orderItem);
	        }
        } else {
        	throw new RuntimeException("Order must contain at least one item");
        }

        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(savedOrder.getId());
        payment.setMethod(request.getMethodPayment());
        payment.setAmount(savedOrder.getTotalAmount());
        payment.setStatus("PAID");
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return mapperAdmin.toAdmin(savedOrder);
    }
    
    // xem chi tiet
    public AdminOrderResponse detail(Long id) {
    	OrderEntity order = repo.findById(id)
    			.orElseThrow(() -> new RuntimeException("Not found"));
    	return mapperAdmin.toAdmin(order);	
    }
    // sua trang thai
    public AdminOrderResponse updateStatus(Long id, String status) {
        OrderEntity order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        order.setCreatedAt(LocalDateTime.now());

        OrderEntity updatedOrder = repo.save(order);
        return mapperAdmin.toAdmin(updatedOrder);
    }

    @Override
    public ClientOrderResponse clientCreate(ClientCreateOrderRequest request) {

        // 1. Create order
        OrderEntity order = new OrderEntity();
        order.setOrderSource("ONLINE"); // client luôn là online
        order.setUserId(request.getUserId());
        order.setStatus("PENDING");
        order.setMethodPayment(request.getMethodPayment());
        order.setAddress(request.getAddress());

        // ❗ client thường không có tableId, employeeId
        order.setTableId(null);
        order.setEmployeeId(null);

        // totalAmount nên tự tính từ items (chuẩn hơn)
        long totalAmount = 0;

        OrderEntity savedOrder = repo.save(order);

        // 2. Create order items
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

        for (CreateOrderItemRequest item : request.getItems()) {

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            totalAmount += item.getPrice() * item.getQuantity();

            orderItemRepository.save(orderItem);
        }

        // 3. Update totalAmount
        savedOrder.setTotalAmount(totalAmount);
        savedOrder.setCreatedAt(LocalDateTime.now());
        repo.save(savedOrder);

        // 4. Payment 
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(savedOrder.getId());
        payment.setMethod(request.getMethodPayment());
        payment.setAmount(totalAmount);

        if ("CASH".equals(request.getMethodPayment())) {
            payment.setStatus("PENDING");
        } else {
            payment.setStatus("PAID");
            payment.setPaidAt(LocalDateTime.now());
        }

        paymentRepository.save(payment);

        return mapper.toClient(savedOrder);
    }
    
    
}