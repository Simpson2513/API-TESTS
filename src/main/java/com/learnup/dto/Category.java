package com.learnup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("products")
    private List<Product> products;
    @JsonProperty("title")
    private String title;

}
