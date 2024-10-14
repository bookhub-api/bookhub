package com.application.jetbill.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "collection_books")
@IdClass(CollectionBookPK.class)
public class CollectionBook {

    @Id
    private Integer book;
    @Id
    private Integer collection;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;
}
