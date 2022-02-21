package com.wattam.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.wattam.controller.exception.RecordNotFoundException;
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

    @Test
    void shouldIndexShoes() throws Exception {

        ShoeDto shoe1 = createShoeDto("Name1", "Color1", 1);
        ShoeDto shoe2 = createShoeDto("Name2", "Color2", 2);
        List<ShoeDto> shoes = ImmutableList
                .<ShoeDto>builder()
                .add(shoe1)
                .add(shoe2)
                .build();

        when(shoeService.index()).thenReturn(shoes);

        mockMvc.perform(get("/shoes").accept(APPLICATION_JSON))
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
    void shouldNotIndexShoes() throws Exception {

        when(shoeService.index()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shoes").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("no shoes found",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void shouldShowShoe() throws Exception {

        ShoeDto shoeDto = createShoeDto("Name", "Color", 1);
        shoeDto.setId("1");

        when(shoeService.show("1")).thenReturn(Optional.of(shoeDto));

        mockMvc.perform(get("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.color", is("Color")))
                .andExpect(jsonPath("$.price", is(1.00)));
    }

    @Test
    void shouldNotShowShoe() throws Exception {

        when(shoeService.show("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("no shoe with the ID: 1",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void shouldStoreShoe() throws Exception {

        String json = "{\"name\": \"Name\",\"color\": \"Color\",\"price\": 1}";

        mockMvc.perform(
                post("/shoes")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateShoe() throws Exception {

        String json = "{\"name\": \"Name\",\"color\": \"Color\",\"price\": 1}";

        ShoeDto shoe = new ShoeDto();

        when(shoeService.show("1")).thenReturn(Optional.of(shoe));
        when(shoeService.update(shoe, "1")).thenReturn(shoe);

        mockMvc.perform(
                put("/shoes/1")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotPutShoe() throws Exception {

        String json = "{\"name\": \"Name\",\"color\": \"Color\",\"price\": 1}";

        mockMvc.perform(put("/shoes/1").accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("no shoe with the ID: 1",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void shouldDeleteShoe() throws Exception {

        ShoeDto shoe = new ShoeDto();

        when(shoeService.show("1")).thenReturn(Optional.of(shoe));

        mockMvc.perform(delete("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteShoe() throws Exception {

        mockMvc.perform(delete("/shoes/1").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result -> assertEquals("no shoe with the ID: 1",
                        result.getResolvedException().getMessage()));
    }
}
