package com.example.doenerbestellung.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderDTO {
    private String username;
    private List<ProductDTO> products;
}
