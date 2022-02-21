package com.wattam.service;

import java.util.List;
import java.util.Optional;

import com.wattam.dto.ShoeDto;

public interface ShoeService {

    public List<ShoeDto> index();

    public Optional<ShoeDto> show(String id);

    public ShoeDto store(ShoeDto shoeDto);

    public ShoeDto update(ShoeDto shoeDto, String id);

    public void delete(String id);
}
