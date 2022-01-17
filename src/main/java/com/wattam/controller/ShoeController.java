package com.wattam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import com.wattam.controller.exception.RecordNotFoundException;
import com.wattam.dto.ShoeDto;
import com.wattam.service.ShoeService;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/shoes")
public class ShoeController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<ShoeDto> getAll() {

        List<ShoeDto> shoes = shoeService.getAllShoes();
        if (shoes == null || shoes.isEmpty()) {
            throw new RecordNotFoundException("no shoes found");
        }
        return shoes;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoeDto get(@PathVariable String id) {

        return shoeService
                .getShoe(id)
                .orElseThrow(() -> new RecordNotFoundException("no shoe with the ID: " + id));
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoeDto post(@Valid @RequestBody ShoeDto shoeDto) {

        return shoeService.addShoe(shoeDto);
    }

    @PutMapping("/put")
    @ResponseStatus(HttpStatus.OK)
    public ShoeDto put(@Valid @RequestBody ShoeDto shoeDto) {

        if (shoeService.getShoe(shoeDto.getId()).isEmpty()) {
            throw new RecordNotFoundException("no shoe with the ID: " + shoeDto.getId());
        }
        return shoeService.addShoe(shoeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {

        if (shoeService.getShoe(id).isEmpty()) {
            throw new RecordNotFoundException("no shoe with the ID: " + id);
        }
        shoeService.deleteShoe(id);
    }

}
