package com.application.jetbill.service;

import com.application.jetbill.dto.PurchaseCreateDTO;
import com.application.jetbill.dto.PurchaseDTO;
import com.application.jetbill.dto.PurchaseReportDTO;
import com.application.jetbill.model.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    PurchaseDTO createPurchase(PurchaseCreateDTO purchaseCreateDTO);
    List<PurchaseDTO> getPurchaseHistoryByUserId(Integer userId);
    List<PurchaseReportDTO> getPurchaseReportByDate();
    List<Purchase> getAllPurchases();
    Purchase confirmPurchase(Integer purchaseId);
    Purchase getPurchaseById(Integer id);
}
