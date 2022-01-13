package com.wattam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.wattam.models.Shoe;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/shoes")
public class ShoeController {

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Shoe> getAll() {

    }

    @GetMapping("/{id}")
    public ResponseEntity<Shoe> get(@PathVariable Long id) {

    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    public Shoe post(@RequestBody Shoe shoe) {

    }

    @PutMapping("/put")
    public ResponseEntity<Shoe> put(@RequestBody Shoe shoe) {

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {

    }

}
