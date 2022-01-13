package com.wattam.controllers;

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

import com.wattam.models.Shoe;
import com.wattam.services.ShoeService;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/shoes")
public class ShoeController {

    @Autowired
    private ShoeService shoeService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Shoe> getAll() {

        return this.shoeService.getAllShoes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Shoe get(@PathVariable String id) {

        return this.shoeService.getShoe(id);
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    public Shoe post(@RequestBody Shoe shoe) {

        return this.shoeService.postShoe(shoe);
    }

    @PutMapping("/put")
    @ResponseStatus(HttpStatus.OK)
    public Shoe put(@RequestBody Shoe shoe) {

        return this.shoeService.putShoe(shoe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {

        this.shoeService.deleteShoe(id);
    }

}
