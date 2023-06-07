package com.realnet.fnd.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SingleSerise  {
    private String name;
    private BigDecimal value;

    public SingleSerise( String name, BigDecimal value){
        this.name  = name;
        this.value = value;
    }
}
