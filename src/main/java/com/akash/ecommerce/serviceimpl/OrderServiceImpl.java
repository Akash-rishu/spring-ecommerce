package com.akash.ecommerce.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.ecommerce.dto.CheckoutRequest;
import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;
import com.akash.ecommerce.entity.Cart;
import com.akash.ecommerce.entity.Order;
import com.akash.ecommerce.entity.OrderItem;
import com.akash.ecommerce.entity.OrderStatus;
import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.CartRepository;
import com.akash.ecommerce.repository.OrderRepository;
import com.akash.ecommerce.repository.ProductRepository;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl
        implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // =====================================
    // ADMIN - GET ALL ORDERS
    // =====================================
    @Override
    public List<OrderResponse> getAllOrders() {

        List<Order> orders =
                orderRepository.findAll();

        List<OrderResponse> responses =
                new ArrayList<>();

        for (Order order : orders) {

            responses.add(
                    mapToResponse(order)
            );
        }

        return responses;
    }

    // =====================================
    // CHECKOUT
    // =====================================
    @Override
    @Transactional
    public OrderResponse checkout(
            Long userId,
            CheckoutRequest request
    ) {

        // FIND USER
        User user =
                userRepository
                .findById(userId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // GET CART
        List<Cart> cartItems =
                cartRepository
                .findByUserId(userId);

        if (cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty"
            );
        }

        // CREATE ORDER
        Order order =
                new Order();

        order.setUser(user);

        order.setStatus(
                OrderStatus.PLACED
        );

        order.setAddress(
                request.getAddress()
        );

        order.setPaymentMethod(
                request.getPaymentMethod()
        );

        List<OrderItem> orderItems =
                new ArrayList<>();

        BigDecimal total =
                BigDecimal.ZERO;

        // LOOP CART ITEMS
        for (Cart cart : cartItems) {

            Product product =
                    cart.getProduct();

            Integer quantity =
                    cart.getQuantity();

            // STOCK CHECK
            if (
                product.getStock()
                < quantity
            ) {

                throw new RuntimeException(

                        "Insufficient stock for "

                        + product.getProductName()
                );
            }

            // REDUCE STOCK
            product.setStock(

                    product.getStock()
                    - quantity
            );

            productRepository.save(
                    product
            );

            // CREATE ORDER ITEM
            OrderItem orderItem =

                    OrderItem.builder()

                    .order(order)

                    .product(product)

                    .quantity(quantity)

                    .price(
                            product.getProductPrice()
                    )

                    .build();

            orderItems.add(
                    orderItem
            );

            // CALCULATE TOTAL
            total = total.add(

                    product
                    .getProductPrice()

                    .multiply(
                            BigDecimal.valueOf(
                                    quantity
                            )
                    )
            );
        }

        // SET DATA
        order.setOrderItems(
                orderItems
        );

        order.setTotalPrice(
                total
        );

        // SAVE ORDER
        Order savedOrder =
                orderRepository.save(
                        order
                );

        // CLEAR CART
        cartRepository.deleteAll(
                cartItems
        );

        return mapToResponse(
                savedOrder
        );
    }

    // =====================================
    // USER - GET OWN ORDERS
    // =====================================
    @Override
    @Transactional
    public List<OrderResponse> getOrdersByUser(
            Long userId
    ) {

        List<Order> orders =

                orderRepository
                .findByUserIdOrderByCreatedAtDesc(
                        userId
                );

        List<OrderResponse> responses =
                new ArrayList<>();

        for (Order order : orders) {

            responses.add(
                    mapToResponse(order)
            );
        }

        return responses;
    }

    // =====================================
    // USER - GET ORDER BY ID
    // =====================================
    @Override
    @Transactional
    public OrderResponse getOrderByIdForUser(
            Long orderId,
            Long userId
    ) {

        Order order =

                orderRepository
                .findById(orderId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        // SECURITY CHECK
        if (
            !order.getUser()
            .getId()
            .equals(userId)
        ) {

            throw new RuntimeException(
                    "Unauthorized access"
            );
        }

        return mapToResponse(order);
    }

    // =====================================
    // CREATE ORDER
    // =====================================
    @Override
    @Transactional
    public OrderResponse createOrder(
            OrderRequest orderRequest,
            Long userId
    ) {

        CheckoutRequest request =
                new CheckoutRequest();

        request.setAddress(
                orderRequest.getAddress()
        );

        request.setPaymentMethod(
                orderRequest.getPaymentMethod()
        );

        return checkout(
                userId,
                request
        );
    }

    // =====================================
    // ADMIN - UPDATE STATUS
    // =====================================
    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,

            OrderStatusUpdateRequest req
    ) {

        Order order =

                orderRepository
                .findById(orderId)

                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"
                        )
                );

        order.setStatus(
                req.getStatus()
        );

        Order updated =
                orderRepository.save(
                        order
                );

        return mapToResponse(
                updated
        );
    }

    // =====================================
    // MAP TO RESPONSE
    // =====================================
    private OrderResponse mapToResponse(
            Order order
    ) {

        List<OrderResponse
                .OrderItemResponse>

                items =
                new ArrayList<>();

        // SAFE NULL CHECK
        if (
            order.getOrderItems()
            != null
        ) {

            for (
                OrderItem oi :
                order.getOrderItems()
            ) {

                OrderResponse
                .OrderItemResponse item =

                        new OrderResponse
                        .OrderItemResponse();

                item.setProductId(

                        oi.getProduct()
                        .getId()
                );

                item.setProductName(

                        oi.getProduct()
                        .getProductName()
                );

                item.setQuantity(
                        oi.getQuantity()
                );

                item.setPrice(
                        oi.getPrice()
                );

                items.add(item);
            }
        }

        // BUILD RESPONSE
        OrderResponse response =
                new OrderResponse();

        response.setId(
                order.getId()
        );

        response.setUserId(

                order.getUser()
                .getId()
        );

        response.setTotalPrice(
                order.getTotalPrice()
        );

        response.setStatus(
                order.getStatus()
        );

        response.setCreatedAt(
                order.getCreatedAt()
        );

        response.setUpdatedAt(
                order.getUpdatedAt()
        );

        response.setAddress(
                order.getAddress()
        );

        response.setPaymentMethod(
                order.getPaymentMethod()
        );

        response.setOrderItems(
                items
        );

        return response;
    }
}