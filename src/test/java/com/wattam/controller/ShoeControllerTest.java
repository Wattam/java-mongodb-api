package com.wattam.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.wattam.dto.ShoeDto;
import com.wattam.service.ShoeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoeController.class)
public class ShoeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    ShoeService shoeService;

    private ShoeDto createShoeDto(String name, String color, double price) {

        ShoeDto shoeDto = new ShoeDto();
        shoeDto.setName(name);
        shoeDto.setColor(color);
        shoeDto.setPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.CEILING));
        return shoeDto;
    }

    @BeforeEach
    public void setUp() {
        Mockito.reset(shoeService);
    }

    // getAll()
    @Test
    void shouldReturnAllShoes() throws Exception {

        ShoeDto shoe1 = createShoeDto("Name1", "Color1", 1);
        ShoeDto shoe2 = createShoeDto("Name2", "Color2", 2);
        List<ShoeDto> shoes = ImmutableList
                .<ShoeDto>builder()
                .add(shoe1)
                .add(shoe2)
                .build();

        when(shoeService.getAllShoes()).thenReturn(shoes);

        mockMvc.perform(get("/shoes/get").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(shoes.size()))
                .andExpect(jsonPath("$[0].name", is("Name1")))
                .andExpect(jsonPath("$[0].color", is("Color1")))
                .andExpect(jsonPath("$[0].price", is(1.00)))
                .andExpect(jsonPath("$[1].name", is("Name2")))
                .andExpect(jsonPath("$[1].color", is("Color2")))
                .andExpect(jsonPath("$[1].price", is(2.00)));
    }

    @Test
    void shouldNotReturnAllShoes() throws Exception {

        when(shoeService.getAllShoes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shoes/get").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // get()
    @Test
    void shouldReturnShoe() throws Exception {

        ShoeDto shoeDto = createShoeDto("Name", "Color", 1);
        shoeDto.setId("1");

        when(shoeService.getShoe("1")).thenReturn(Optional.of(shoeDto));

        mockMvc.perform(get("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.color", is("Color")))
                .andExpect(jsonPath("$.price", is(1.00)));
    }

    @Test
    void shouldNotReturnShoe() throws Exception {

        when(shoeService.getShoe("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPostShoe() throws Exception {

        String json = "{\"name\": \"Name\",\"color\": \"Color\",\"price\": 1}";

        ShoeDto shoe = new ShoeDto();
        shoe.setName("Name");
        shoe.setColor("Color");
        shoe.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));

        ShoeDto shoePosted = new ShoeDto();
        shoePosted.setName("Name");
        shoePosted.setColor("Color");
        shoePosted.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));
        shoePosted.setId("1");

        when(shoeService.addShoe(shoe)).thenReturn(shoePosted);

        mockMvc.perform(
                post("/shoes/post")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testPut() throws Exception {

        String json = "{\"id\": \"1\",\"name\": \"Name\",\"color\": \"Color\",\"price\": 1}";

        ShoeDto shoe = new ShoeDto();
        shoe.setId("1");
        shoe.setName("Name");
        shoe.setColor("Color");
        shoe.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));

        when(shoeService.addShoe(shoe)).thenReturn(shoe);
        when(shoeService.getShoe(shoe.getId())).thenReturn(Optional.of(shoe));

        mockMvc.perform(
                put("/shoes/put")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {

        ShoeDto shoe = new ShoeDto();
        shoe.setId("1");
        shoe.setName("Name");
        shoe.setColor("Color");
        shoe.setPrice(BigDecimal.valueOf(1).setScale(2, RoundingMode.CEILING));

        when(shoeService.getShoe(shoe.getId())).thenReturn(Optional.of(shoe));

        mockMvc.perform(delete("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
