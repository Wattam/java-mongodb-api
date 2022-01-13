package com.wattam.services;

import java.util.List;

import com.wattam.models.Shoe;

public interface ShoeService {

    public List<Shoe> getAllShoes();

    public Shoe getShoe(String id);

    public Shoe postShoe(Shoe shoe);

    public Shoe putShoe(Shoe shoe);

    public void deleteShoe(String id);

}
