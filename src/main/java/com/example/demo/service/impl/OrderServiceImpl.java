package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.CreateOrderItemRequest;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.response.AdminOrderResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.IngredientEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.PaymentEntity;
import com.example.demo.entity.ProductIngredientEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.mapper.OrderMapperToAdmin;
import com.example.demo.mapper.OrderMapperToClient;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductIngredientRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.request.ClientCreateOrderRequest;
import com.example.demo.dto.response.ClientOrderResponse;
import com.example.demo.service.OrderService;
import com.example.demo.specification.OrderSpecification;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ProductIngredientRepository productingredientRepository;
    private final IngredientRepository ingredientRepository;
    private final OrderMapperToClient mapper;
	private final OrderMapperToAdmin mapperAdmin;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(
            OrderRepository repo,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository,
            ProductIngredientRepository productIngredientRepository,
            IngredientRepository ingredientRepository,
            OrderMapperToClient mapper,
            OrderMapperToAdmin mapperAdmin,
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            ProductRepository productRepository
    ) {
        this.repo = repo;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.productingredientRepository = productIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
        this.mapperAdmin = mapperAdmin;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
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
    	AdminOrderResponse response = mapperAdmin.toAdmin(order);
    	response.setAddress(order.getAddress());

    	if (order.getTableId() != null && "OFFLINE".equalsIgnoreCase(order.getOrderSource())) {
    		response.setTableLabel("Table " + order.getTableId());
    	}

    	if (order.getUserId() != null) {
    		userRepository.findById(order.getUserId()).ifPresent(user -> response.setCustomer(toUserSummary(user)));
    	}

    	if (order.getEmployeeId() != null) {
    		employeeRepository.findById(order.getEmployeeId()).ifPresent(employee -> response.setEmployee(toEmployeeSummary(employee)));
    	}

    	paymentRepository.findFirstByOrderIdOrderByIdDesc(order.getId())
    			.ifPresent(payment -> response.setPayment(toPaymentSummary(payment)));

    	List<OrderItemEntity> orderItems = orderItemRepository.findByOrderId(order.getId());
    	response.setItems(toOrderItemDetails(orderItems));

    	return response;	
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
    @Transactional
    @Override
    public ClientOrderResponse clientCreate(ClientCreateOrderRequest request) {
    	
    	if(request.getItems() == null || request.getItems().isEmpty()) {
    		throw new RuntimeException("Order must contain at least one item");
    	}
    	
    	// Tinh tong nguyen lieu can 
    	Map<Long, Long> requiredIngredients = new HashMap<>();
    	
    	for(CreateOrderItemRequest item : request.getItems()) {
    		List<ProductIngredientEntity> recipe = productingredientRepository.findByProductId(item.getProductId());
    		
    		for(ProductIngredientEntity pi : recipe) {
    			Long ingredientId = pi.getIngredientId();
    			
    			Long need = pi.getAmount() * item.getQuantity();
    			
    			if (requiredIngredients.containsKey(ingredientId)) {
    			    Long oldValue = requiredIngredients.get(ingredientId);
    			    requiredIngredients.put(ingredientId, oldValue + need);
    			} else {
    			    requiredIngredients.put(ingredientId, need);
    			}
    		}
    		
    	}
    	
    	List<IngredientEntity> ingredients = ingredientRepository.findAllByIdForUpdate(requiredIngredients.keySet());
    	
    	// convert sang map
    	Map<Long, IngredientEntity> ingredientMap = ingredients.stream()
    			.collect(Collectors.toMap(IngredientEntity::getId, i -> i));
    	// Check du 
    	for(Map.Entry<Long, Long> entry : requiredIngredients.entrySet()) {
    		IngredientEntity in = ingredientMap.get(entry.getKey());
    		
    		if(in.getStock().compareTo(entry.getValue()) < 0) {
    			throw new RuntimeException("Not enough ingredient: " + in.getName());
    		}
    	}
    	
    	// Tru stock
    	for(Map.Entry<Long, Long> entry : requiredIngredients.entrySet()) {
    		IngredientEntity in = ingredientMap.get(entry.getKey());
    		
    		in.setStock(in.getStock() - entry.getValue());
    	}
    	
    	ingredientRepository.saveAll(ingredients);
    	
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
    
    
    private AdminOrderResponse.UserSummary toUserSummary(UserEntity user) {
    	return new AdminOrderResponse.UserSummary(
    			user.getId(),
    			user.getName(),
    			user.getUsername(),
    			user.getEmail()
    	);
    }

    private AdminOrderResponse.EmployeeSummary toEmployeeSummary(EmployeeEntity employee) {
    	return new AdminOrderResponse.EmployeeSummary(
    			employee.getId(),
    			employee.getName(),
    			employee.getPosition(),
    			employee.getPhone()
    	);
    }

    private AdminOrderResponse.PaymentSummary toPaymentSummary(PaymentEntity payment) {
    	return new AdminOrderResponse.PaymentSummary(
    			payment.getId(),
    			payment.getMethod(),
    			payment.getAmount(),
    			payment.getStatus(),
    			payment.getPaidAt()
    	);
    }

    private List<AdminOrderResponse.OrderItemDetail> toOrderItemDetails(List<OrderItemEntity> orderItems) {
    	if (orderItems == null || orderItems.isEmpty()) {
    		return Collections.emptyList();
    	}

    	Set<Long> productIds = orderItems.stream()
    			.map(OrderItemEntity::getProductId)
    			.collect(Collectors.toSet());

    	Map<Long, String> productNames = productRepository.findAllById(productIds).stream()
    			.collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getName));

    	return orderItems.stream()
    			.map(item -> new AdminOrderResponse.OrderItemDetail(
    					item.getId(),
    					item.getProductId(),
    					productNames.getOrDefault(item.getProductId(), "Unknown product"),
    					item.getQuantity(),
    					item.getPrice(),
    					item.getPrice() == null || item.getQuantity() == null
    							? null
    							: Long.valueOf(item.getPrice()) * item.getQuantity()
    			))
    			.collect(Collectors.toList());
    }
}
