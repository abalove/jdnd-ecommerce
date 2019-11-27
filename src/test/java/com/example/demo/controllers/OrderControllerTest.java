package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepo);
        TestUtils.injectObject(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void verify_submit_order() {
        // Create a dummy cart and user
        Cart c = new Cart();
        c.setId(0L);
        User u = new User();
        u.setUsername("Aaron");
        u.setCart(c);
        c.setUser(u);

        // Create a dummy item
        Item i = new Item();
        i.setId(0L);
        i.setName("My test item");
        i.setPrice(BigDecimal.valueOf(10.99));

        // Add the item to the cart
        c.addItem(i);

        // Stub the user repo
        when(userRepo.findByUsername(u.getUsername())).thenReturn(u);

        final ResponseEntity<UserOrder> response = orderController.submit(u.getUsername());
        UserOrder order = response.getBody();
        assertEquals(BigDecimal.valueOf(10.99), order.getTotal());
    }
}
