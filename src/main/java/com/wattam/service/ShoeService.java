package com.wattam.service;

import java.util.List;
import java.util.Optional;

import com.wattam.dto.ShoeDto;

public interface ShoeService {

    public List<ShoeDto> getAllShoes();

    public Optional<ShoeDto> getShoe(String id);

    public ShoeDto addShoe(ShoeDto shoeDto);

    public void deleteShoe(String id);

}
