package com.wattam.services;

import java.util.List;

import com.wattam.models.Shoe;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShoeServiceImpl implements ShoeService{

    @Override
    public List<Shoe> getAllShoes() {
        
        return null;
    }

    @Override
    public ResponseEntity<Shoe> getShoe(Long id) {
        

        return null;
    }

    @Override
    public Shoe postShoe(Shoe shoe) {
        
        
        return null;
    }

    @Override
    public ResponseEntity<Shoe> putShoe(Shoe shoe) {
        
        
        return null;
    }

    @Override
    public void deleteShoe(Long id) {
        
        
    }
}
