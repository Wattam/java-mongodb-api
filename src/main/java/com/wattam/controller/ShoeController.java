package com.wattam.controller;

import java.util.List;

import javax.validation.Valid;

import com.wattam.controller.exception.RecordNotFoundException;
import com.wattam.dto.ShoeDto;
import com.wattam.service.ShoeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoes")
public class ShoeController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoeDto> index() {

        List<ShoeDto> shoes = shoeService.index();
        if (shoes == null || shoes.isEmpty()) {
            throw new RecordNotFoundException("no shoes found");
        }
        return shoes;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoeDto show(@PathVariable String id) {

        return shoeService
                .show(id)
                .orElseThrow(() -> new RecordNotFoundException("no shoe with the ID: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoeDto store(@Valid @RequestBody ShoeDto shoeDto) {

        return shoeService.store(shoeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoeDto update(@Valid @RequestBody ShoeDto shoeDto, @PathVariable String id) {

        if (shoeService.show(id).isEmpty()) {
            throw new RecordNotFoundException("no shoe with the ID: " + id);
        }
        return shoeService.update(shoeDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {

        if (shoeService.show(id).isEmpty()) {
            throw new RecordNotFoundException("no shoe with the ID: " + id);
        }
        shoeService.delete(id);
    }
}
