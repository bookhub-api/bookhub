package com.application.jetbill.service.impl;

import com.application.jetbill.dto.PurchaseCreateDTO;
import com.application.jetbill.dto.PurchaseDTO;
import com.application.jetbill.dto.PurchaseReportDTO;
import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.mapper.PurchaseMapper;
import com.application.jetbill.model.entity.Book;
import com.application.jetbill.model.entity.Purchase;
import com.application.jetbill.model.enums.PaymentStatus;
import com.application.jetbill.repository.BookRepository;
import com.application.jetbill.repository.PurchaseRepository;
import com.application.jetbill.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseDTO createPurchase(PurchaseCreateDTO purchaseCreateDTO) {
        //Convertir PurchaseCreateDTO a Purchase
        Purchase purchase = purchaseMapper.toPurchaseCreateDTO(purchaseCreateDTO);
        Float total = purchase.getItems()
                .stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0f, Float::sum);

        purchase.setCreatedAt(LocalDateTime.now());
        purchase.setPaymentStatus(PaymentStatus.PENDING);
        purchase.setTotal(total);
        purchase.getItems().forEach(item -> item.setPurchase(purchase));
        Purchase savePurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toPurchaseDTO(savePurchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getPurchaseHistoryByUserId(Integer userId) {
        return purchaseRepository.findByCustomerId(userId).stream()
                .map(purchaseMapper::toPurchaseDTO)
                .toList();
    }

    @Override
    public List<PurchaseReportDTO> getPurchaseReportByDate() {
        return null;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return null;
    }

    @Override
    public Purchase confirmPurchase(Integer purchaseId) {
        return null;
    }

    @Override
    public Purchase getPurchaseById(Integer id) {
        return null;
    }
}
