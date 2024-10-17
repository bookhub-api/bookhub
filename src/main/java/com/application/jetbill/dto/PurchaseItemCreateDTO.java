package com.application.jetbill.dto;

import lombok.Data;

@Data
public class PurchaseItemCreateDTO {
    private Integer bookId;
    private Integer quantity;
    private Float price;
}
