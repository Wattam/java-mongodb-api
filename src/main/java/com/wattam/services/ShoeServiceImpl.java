package com.wattam.services;

import java.util.List;

import com.wattam.errors.RecordNotFoundException;
import com.wattam.models.Shoe;
import com.wattam.repository.ShoeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoeServiceImpl implements ShoeService {

    @Autowired
    private ShoeRepository shoeRepository;

    @Override
    public List<Shoe> getAllShoes() {

        return this.shoeRepository.findAll();
    }

    @Override
    public Shoe getShoe(String id) {

        return this.shoeRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("no shoe with the ID: " + id));
    }

    @Override
    public Shoe postShoe(Shoe shoe) {

        return this.shoeRepository.save(shoe);
    }

    @Override
    public Shoe putShoe(Shoe shoe) {

        if (shoeRepository.findById(shoe.getId()).isEmpty()) {
            throw new RecordNotFoundException("no user with the ID: " + shoe.getId());
        }

        return this.shoeRepository.save(shoe);
    }

    @Override
    public void deleteShoe(String id) {

        if (shoeRepository.findById(id).isEmpty()) {
            throw new RecordNotFoundException("no user with the ID: " + id);
        }

        this.shoeRepository.deleteById(id);
    }

}
