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
    public OrderResponse create(CreateOrderRequest request) {
        if("WEB".equalsIgnoreCase(request.getOrderSource())){
            //muaonline
            //  create order
            OrderEntity order = new OrderEntity();
            order.setOrderSource(request.getOrderSource());
            order.setTableId(request.getTableId());
            order.setUserId(request.getUserId());
            order.setEmployeeId(request.getEmployeeId());
            order.setStatus(request.getStatus());
            order.setTotalAmount(request.getTotalAmount());

            if (request.getCreated_at() != null) {
                order.setCreatedAt(
                        request.getCreated_at().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime()
                );
            }
            else {
                order.setCreatedAt(LocalDateTime.now());
            }
        
            OrderEntity savedOrder = repo.save(order);

            if (request.getItems() != null) {
                for(CreateOrderItemRequest item : request.getItems()) {
                    OrderItemEntity orderItem = new OrderItemEntity();
                    orderItem.setOrderId(savedOrder.getId());
                    orderItem.setProductId(item.getProductId());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());

                    orderItemRepository.save(orderItem);
                }
            }
            else throw new RuntimeException("Phai co it nhat 1 san pham");

            PaymentEntity payment = new PaymentEntity();
            payment.setOrderId(savedOrder.getId());
            payment.setMethod(request.getPaymentMethod());
            payment.setAmount(savedOrder.getTotalAmount());
            payment.setStatus("PAID");
            payment.setPaidAt(LocalDateTime.now());

            paymentRepository.save(payment);

            return mapper.toClient(savedOrder);
        }
        else{
            //muaoffline
            //  create order
            OrderEntity order = new OrderEntity();
            order.setOrderSource(request.getOrderSource());
            order.setTableId(request.getTableId());
            order.setUserId(request.getUserId());
            order.setEmployeeId(request.getEmployeeId());
            order.setStatus(request.getStatus());
            order.setTotalAmount(request.getTotalAmount());

            if (request.getCreated_at() != null) {
                order.setCreatedAt(
                        request.getCreated_at().toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime()
                );
            } 
            else {
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
            payment.setMethod(request.getPaymentMethod());
            payment.setAmount(savedOrder.getTotalAmount());
            payment.setStatus("PAID");
            payment.setPaidAt(LocalDateTime.now());

            paymentRepository.save(payment);

            return mapper.toClient(savedOrder);
        }
    }
}