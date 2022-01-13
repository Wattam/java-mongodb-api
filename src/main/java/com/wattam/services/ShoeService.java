package com.wattam.services;

import java.util.List;

import com.wattam.models.Shoe;

import org.springframework.http.ResponseEntity;

public interface ShoeService {
    
    public List<Shoe> getAllShoes();

    public ResponseEntity<Shoe> getShoe( Long id);

    public Shoe postShoe(Shoe shoe);

    public ResponseEntity<Shoe> putShoe(Shoe shoe);

    public void deleteShoe(Long id);

}
