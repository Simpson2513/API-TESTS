package com.learnup.enums;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food"),
    FURNITURE(567, "furniture");

    @Getter
    private int id;
    @Getter
    private String name;
    CategoryType( int id,  String name) {
        this.id = id;
        this.name = name;
    }
}
