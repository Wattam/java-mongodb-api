package com.wattam.repository;

import com.wattam.models.Shoe;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoeRepository extends MongoRepository<Shoe, String> {
    
}
