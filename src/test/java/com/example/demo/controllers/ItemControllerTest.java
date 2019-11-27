package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_getItemById() {
        Item i = new Item();
        i.setId(0L);
        i.setPrice(BigDecimal.valueOf(10.99));
        i.setName("Udacity JDND");
        i.setDescription("Udacity Java Development Nanodegree Subscription");

        when(itemRepo.findById(0L)).thenReturn(Optional.of(i));
        ResponseEntity<Item> response = itemController.getItemById(0L);

        Item item = response.getBody();

        assertEquals(BigDecimal.valueOf(10.99), item.getPrice());
        assertEquals("Udacity JDND", item.getName());
        assertEquals("Udacity Java Development Nanodegree Subscription", item.getDescription());
    }

    @Test
    public void verify_getItems() {
        List<Item> items = new ArrayList<>();
        Item i = new Item();
        i.setId(0L);
        i.setPrice(BigDecimal.valueOf(10.99));
        i.setName("Udacity JDND");
        i.setDescription("Udacity Java Development Nanodegree Subscription");

        Item i2 = new Item();
        i2.setId(1L);
        i2.setPrice(BigDecimal.valueOf(12.99));
        i2.setName("Udacity Java Intro");
        i2.setDescription("Udacity Java Introduction Online Course");

        items.add(i);
        items.add(i2);

        when(itemRepo.findAll()).thenReturn(items);
        ResponseEntity<List<Item>> response = itemController.getItems();
        List<Item> itemsResponse = response.getBody();
        assertTrue(itemsResponse.size() == 2);
    }
}
