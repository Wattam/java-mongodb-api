package com.wattam.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.wattam.dto.ShoeDto;
import com.wattam.model.Shoe;
import com.wattam.repository.ShoeRepository;
import com.wattam.service.ShoeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest(properties = { "spring.mongodb.embedded.version=3.2.8" })
@ExtendWith(SpringExtension.class)
public class ShoeServiceImplTest {

    @Autowired
    private ShoeRepository shoeRepository;

    private ShoeService shoeService;

    @BeforeEach
    void beforeEach() {

        shoeService = new ShoeServiceImpl(shoeRepository);
    }

    private Shoe createShoe(String id, String name, String color, double price) {

        Shoe shoe = new Shoe();
        shoe.setId(id);
        shoe.setName(name);
        shoe.setColor(color);
        shoe.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.CEILING));
        return shoe;
    }

    // getAllShoes()
    @Test
    void shouldReturnAllShoes() {

        shoeRepository.save(createShoe("1", "Name1", "Color1", 1));
        shoeRepository.save(createShoe("2", "Name2", "Color2", 2));

        List<ShoeDto> shoes = shoeService.getAllShoes();

        assertEquals(2, shoes.size());

        assertEquals("1", shoes.get(0).getId());
        assertEquals("Name1", shoes.get(0).getName());
        assertEquals("Color1", shoes.get(0).getColor());
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING), shoes.get(0).getPrice());

        assertEquals("2", shoes.get(1).getId());
        assertEquals("Name2", shoes.get(1).getName());
        assertEquals("Color2", shoes.get(1).getColor());
        assertEquals(BigDecimal.valueOf(2).setScale(2, RoundingMode.CEILING), shoes.get(1).getPrice());
    }

    @Test
    void shouldNotReturnAllShoes() {

        List<ShoeDto> shoes = shoeService.getAllShoes();

        assertEquals(0, shoes.size());
    }

    // getShoe()
    @Test
    void shouldReturnShoe() {

        Shoe expected = shoeRepository.save(createShoe("1", "Name", "Color", 1.11));

        Shoe actual = shoeService.getShoe("1").get().toEntity();

        assertEquals(expected, actual);
        assertEquals("1", actual.getId());
        assertEquals("Name", actual.getName());
        assertEquals("Color", actual.getColor());
        assertEquals(BigDecimal.valueOf(1.11).setScale(2, RoundingMode.CEILING), actual.getPrice());
    }

    @Test
    void shouldNotReturnShoe() {

        ShoeDto actual = shoeService.getShoe("1").orElse(actual = null);

        assertFalse(shoeService.getShoe("1").isPresent());
        assertEquals(null, actual);
    }

    // addShoe()
    @Test
    void shouldAddShoe() {

        ShoeDto expected = new ShoeDto();
        expected.setName("Name");
        expected.setColor("Color");
        expected.setPrice(BigDecimal.valueOf(1.11));

        ShoeDto addedShoe = shoeService.addShoe(expected);
        assertNotNull(addedShoe);

        ShoeDto actual = shoeRepository.findById(addedShoe.getId()).map(ShoeDto::of).get();

        assertEquals("Name", actual.getName());
        assertEquals("Color", actual.getColor());
        assertEquals(BigDecimal.valueOf(1.11).setScale(2, RoundingMode.CEILING), actual.getPrice());

        shoeRepository.deleteById(addedShoe.getId());
    }

    // deleteShoe()
    @Test
    void shouldDeleteShoe() {

        shoeRepository.save(createShoe("1", "Name", "Color", 1.11));
        assertNotNull(shoeService.getShoe("1").get());

        shoeService.deleteShoe("1");

        ShoeDto actual = shoeService.getShoe("1").orElse(actual = null);

        assertEquals(null, actual);
    }
}
