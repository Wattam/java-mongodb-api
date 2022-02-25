package com.wattam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.wattam.dto.ShoeDto;
import com.wattam.model.Shoe;
import com.wattam.repository.ShoeRepository;
import com.wattam.service.impl.ShoeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest(properties = { "spring.mongodb.embedded.version=3.4.1" })
@ExtendWith(SpringExtension.class)
public class ShoeServiceTest {

    private ShoeService shoeService;

    @Autowired
    private ShoeRepository shoeRepository;

    @BeforeEach
    public void setUp() {
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

    @Test
    void shouldIndexShoes() {

        shoeRepository.save(createShoe("1", "Name1", "Color1", 1));
        shoeRepository.save(createShoe("2", "Name2", "Color2", 2));

        List<ShoeDto> shoes = shoeService.index();

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
    void shouldNotIndexShoes() {

        List<ShoeDto> shoes = shoeService.index();

        assertEquals(0, shoes.size());
    }

    @Test
    void shouldReturnShoe() {

        Shoe expected = shoeRepository.save(createShoe("1", "Name", "Color", 1.11));

        Shoe actual = shoeService.show("1").get().toEntity();

        assertEquals(expected, actual);
        assertEquals("1", actual.getId());
        assertEquals("Name", actual.getName());
        assertEquals("Color", actual.getColor());
        assertEquals(BigDecimal.valueOf(1.11).setScale(2, RoundingMode.CEILING), actual.getPrice());
    }

    @Test
    void shouldNotReturnShoe() {

        assertFalse(shoeService.show("1").isPresent());
    }

    @Test
    @DirtiesContext
    void shouldStoreShoe() {

        ShoeDto expected = new ShoeDto();
        expected.setName("Name");
        expected.setColor("Color");
        expected.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));

        ShoeDto addedShoe = shoeService.store(expected);
        assertNotNull(addedShoe);

        ShoeDto actual = shoeRepository.findById(addedShoe.getId()).map(ShoeDto::of).get();

        assertEquals("Name", actual.getName());
        assertEquals("Color", actual.getColor());
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING), actual.getPrice());

        shoeRepository.deleteById(addedShoe.getId());
    }

    @Test
    @DirtiesContext
    void shouldUpdateShoe() {

        Shoe shoe = new Shoe();

        shoe = shoeRepository.save(shoe);

        ShoeDto expected = new ShoeDto();
        expected.setName("Name");
        expected.setColor("Color");
        expected.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));

        ShoeDto updatedShoe = shoeService.update(expected, shoe.getId());
        assertNotNull(updatedShoe);

        ShoeDto actual = shoeRepository.findById(updatedShoe.getId()).map(ShoeDto::of).get();

        assertEquals("Name", actual.getName());
        assertEquals("Color", actual.getColor());
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING), actual.getPrice());
    }

    @Test
    void shouldDeleteShoe() {

        shoeRepository.save(createShoe("1", "Name", "Color", 1.11));
        assertNotNull(shoeService.show("1").get());

        shoeService.delete("1");

        assertFalse(shoeService.show("1").isPresent());
    }
}
