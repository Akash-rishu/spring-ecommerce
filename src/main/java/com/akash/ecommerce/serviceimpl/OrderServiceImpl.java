package com.akash.ecommerce.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======
import com.akash.ecommerce.dto.CheckoutRequest;
>>>>>>> b654978 (My code)
import com.akash.ecommerce.dto.OrderRequest;
import com.akash.ecommerce.dto.OrderResponse;
import com.akash.ecommerce.dto.OrderStatusUpdateRequest;
import com.akash.ecommerce.entity.Cart;
import com.akash.ecommerce.entity.Order;
import com.akash.ecommerce.entity.OrderItem;
<<<<<<< HEAD
=======
import com.akash.ecommerce.entity.OrderStatus;
>>>>>>> b654978 (My code)
import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.entity.User;
import com.akash.ecommerce.repository.CartRepository;
import com.akash.ecommerce.repository.OrderRepository;
import com.akash.ecommerce.repository.ProductRepository;
import com.akash.ecommerce.repository.UserRepository;
import com.akash.ecommerce.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    // ADMIN ONLY (enforced in controller with @PreAuthorize)
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> res = new ArrayList<>();
        for (Order o : orders) {
            res.add(mapToResponse(o));
        }
        return res;
    }

<<<<<<< HEAD
=======
    @Override
    public OrderResponse checkout(
        Long userId,
        CheckoutRequest request
    ) {

    OrderRequest orderRequest =
            new OrderRequest();

    return createOrder(
            orderRequest,
            userId
    );
}

>>>>>>> b654978 (My code)
    // USER - list own orders
    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> res = new ArrayList<>();
        for (Order o : orders) {
            res.add(mapToResponse(o));
        }
        return res;
    }

    // USER - get one order with ownership check
    @Override
    public OrderResponse getOrderByIdForUser(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        return mapToResponse(order);
    }

    // USER - create order from cart (secure)
    @Override
<<<<<<< HEAD
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1) fetch cart
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2) create order
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 3) build order items + validate stock
        for (Cart cart : cartItems) {

            Product product = cart.getProduct();

            int qty = cart.getQuantity();
            if (qty <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            // stock check
            if (product.getStock() < qty) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getProductName());
            }

            // deduct stock
            product.setStock(product.getStock() - qty);
            productRepository.save(product);

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(qty)
                    .price(product.getProductPrice()) // price at purchase time
                    .build();

            orderItems.add(item);

            total = total.add(
                    product.getProductPrice().multiply(BigDecimal.valueOf(qty))
            );
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        // 4) save order (cascades items)
        Order saved = orderRepository.save(order);

        // 5) clear cart
        cartRepository.deleteAll(cartItems);

        return mapToResponse(saved);
=======
@Transactional
public OrderResponse createOrder(
        OrderRequest orderRequest,
        Long userId
) {

    User user = userRepository.findById(userId)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    // Fetch Cart Items
    List<Cart> cartItems =
            cartRepository.findByUserId(userId);

    if (cartItems.isEmpty()) {

        throw new RuntimeException(
                "Cart is empty"
        );
    }

    // Create Order
    Order order = new Order();

    order.setUser(user);

    order.setStatus(OrderStatus.PLACED);

    List<OrderItem> orderItems =
            new ArrayList<>();

    BigDecimal total =
            BigDecimal.ZERO;

    // Convert Cart -> Order Items
    for (Cart cart : cartItems) {

        Product product =
                cart.getProduct();

        int qty =
                cart.getQuantity();

        // Validate Quantity
        if (qty <= 0) {

            throw new RuntimeException(
                    "Invalid quantity"
            );
        }

        // Validate Stock
        if (product.getStock() < qty) {

            throw new RuntimeException(
                    "Insufficient stock for: "
                            + product.getProductName()
            );
        }

        // Reduce Stock
        product.setStock(
                product.getStock() - qty
        );

        productRepository.save(product);

        // Create Order Item
        OrderItem item =
                OrderItem.builder()
                        .order(order)
                        .product(product)
                        .quantity(qty)
                        .price(
                                product.getProductPrice()
                        )
                        .build();

        orderItems.add(item);

        // Calculate Total
        total = total.add(
                product.getProductPrice()
                        .multiply(
                                BigDecimal.valueOf(qty)
                        )
        );
    }

    // Set Order Data
    order.setOrderItems(orderItems);

    order.setTotalPrice(total);

    // Save Order
    Order savedOrder =
            orderRepository.save(order);

    // Clear Cart
    cartRepository.deleteAll(cartItems);

    return mapToResponse(savedOrder);
>>>>>>> b654978 (My code)
    }

    // ADMIN - update status
    @Override
    public OrderResponse updateOrderStatus(Long orderId,
                                           OrderStatusUpdateRequest req) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(req.getStatus());

        Order updated = orderRepository.save(order);
        return mapToResponse(updated);
    }

    // ------------------ MAPPER ------------------

    private OrderResponse mapToResponse(Order order) {

        List<OrderResponse.OrderItemResponse> items = new ArrayList<>();

        if (order.getOrderItems() != null) {
    for (OrderItem oi : order.getOrderItems()) {
        items.add(new OrderResponse.OrderItemResponse(
                oi.getProduct().getId(),
                oi.getProduct().getProductName(),
                oi.getQuantity(),
                oi.getPrice()
        ));
    }
}

        return new OrderResponse(
        order.getId(),
        order.getUser().getId(),
        order.getTotalPrice(),
        order.getStatus(),
        order.getCreatedAt(),
<<<<<<< HEAD
        order.getUpdatedAt(),   
=======
        order.getUpdatedAt(),
        order.getAddress(),
        order.getPaymentMethod(),
>>>>>>> b654978 (My code)
        items
);
    }
}