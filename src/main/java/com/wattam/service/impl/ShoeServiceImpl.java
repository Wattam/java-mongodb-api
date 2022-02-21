package com.wattam.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.wattam.dto.ShoeDto;
import com.wattam.model.Shoe;
import com.wattam.repository.ShoeRepository;
import com.wattam.service.ShoeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoeServiceImpl implements ShoeService {

    @Autowired
    public ShoeRepository shoeRepository;

    public ShoeServiceImpl(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @Override
    public List<ShoeDto> index() {

        return shoeRepository
                .findAll()
                .stream()
                .map(ShoeDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShoeDto> show(String id) {

        return shoeRepository.findById(id).map(ShoeDto::of);
    }

    @Override
    public ShoeDto store(ShoeDto shoeDto) {

        Shoe postedShoe = shoeRepository.save(shoeDto.toEntity());
        return ShoeDto.of(postedShoe);
    }

    @Override
    public ShoeDto update(ShoeDto shoeDto, String id) {

        shoeDto.setId(id);
        Shoe postedShoe = shoeRepository.save(shoeDto.toEntity());
        return ShoeDto.of(postedShoe);
    }

    @Override
    public void delete(String id) {

        shoeRepository.deleteById(id);
    }
}
