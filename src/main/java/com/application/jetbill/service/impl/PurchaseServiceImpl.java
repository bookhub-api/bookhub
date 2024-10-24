package com.application.jetbill.service.impl;

import com.application.jetbill.dto.PurchaseCreateDTO;
import com.application.jetbill.dto.PurchaseDTO;
import com.application.jetbill.dto.PurchaseReportDTO;
import com.application.jetbill.exception.BadRequestException;
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
        // Verificar si los libros existen en la base de datos antes de proceder
        purchase.getItems().forEach(item -> {
            Book book = bookRepository.findById(item.getBook().getId())
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + item.getBook().getId()));
            item.setBook(book); // Asociar el libro existente al PurchaseItem
            item.setPurchase(purchase); // Asociar el PurchaseItem a la compra actual
        });
        purchase.setCreatedAt(LocalDateTime.now());
        purchase.setPaymentStatus(PaymentStatus.PENDING);

        // Calcular el total basado en la cantidad de libros comprados
        Float total = purchase.getItems()
                .stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0f, Float::sum);

        purchase.setTotal(total);//se asigna el total de la compra
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toPurchaseDTO(savedPurchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getPurchaseHistoryByUserId(Integer userId) {
        return purchaseRepository.findByCustomerId(userId).stream()
                .map(purchaseMapper::toPurchaseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PurchaseReportDTO> getPurchaseReportByDate() {
        try {
            return purchaseRepository.getPurchaseReportByDate().stream()
                    .map(result -> new PurchaseReportDTO (
                            (Integer) result[0],
                            (String)result[1])).toList();

        }catch (ClassCastException e){
            throw new BadRequestException("Error al convertir los resultados de la consulta "+e);
        }catch (Exception e){
            throw new ResourceNotFoundException("Ocurrió un error al obtener el reporte de compras.");
        }

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
