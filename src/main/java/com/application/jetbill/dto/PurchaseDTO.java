package com.application.jetbill.dto;

import com.application.jetbill.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseDTO {
    private Integer id;
    private Float total;
    private LocalDateTime createdAt;
    private PaymentStatus paymentStatus;
    private String customerName;
    private List<PurchaseItemDTO> items;
}
