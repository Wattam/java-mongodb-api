package com.wattam.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.opengamma.strata.collect.ArgChecker;
import com.wattam.model.Shoe;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class ShoeDto implements Serializable {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String color;
    private BigDecimal price;

    public Shoe toEntity() {

        Shoe shoe = new Shoe();
        BeanUtils.copyProperties(this, shoe);
        return shoe;
    }

    public static ShoeDto of(Shoe shoe) {

        ArgChecker.notNull(shoe, "shoe");
        ShoeDto shoeDto = new ShoeDto();
        BeanUtils.copyProperties(shoe, shoeDto);
        return shoeDto;
    }
}