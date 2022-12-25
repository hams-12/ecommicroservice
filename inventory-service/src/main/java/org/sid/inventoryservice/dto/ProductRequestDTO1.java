package org.sid.inventoryservice.dto;

import lombok.Data;

@Data
public class ProductRequestDTO1 {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private Long categoryId;
}
