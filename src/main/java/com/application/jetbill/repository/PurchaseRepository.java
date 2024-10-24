package com.application.jetbill.repository;
import com.application.jetbill.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
    List<Purchase> findByCustomerId(Integer customerId);
    @Query(value = "SELECT * FROM fn_list_purchase_report() ", nativeQuery = true)
    List<Object[]> getPurchaseReportByDate();
}
