package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private UserController userController;

    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepo);
        TestUtils.injectObject(cartController, "cartRepository", cartRepo);
        TestUtils.injectObject(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_add_to_cart() {
        // Create a cart request
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername("Aaron");

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

        // Stub the user repo
        when(userRepo.findByUsername(request.getUsername())).thenReturn(u);

        // Stub the item repo
        when(itemRepo.findById(i.getId())).thenReturn(Optional.of(i));

        final ResponseEntity<Cart> response = cartController.addTocart(request);
        Cart c2 = response.getBody();
        assertEquals("Aaron", c2.getUser().getUsername());
        assertEquals(1, c2.getItems().size());
    }

    @Test
    public void verify_remove_from_cart() {
        // Create a cart request
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername("Aaron");

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

        // Stub the user repo
        when(userRepo.findByUsername(request.getUsername())).thenReturn(u);

        // Stub the item repo
        when(itemRepo.findById(i.getId())).thenReturn(Optional.of(i));

        final ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Cart c2 = response.getBody();
        assertTrue(c2.getItems().size() == 0);
    }
}
