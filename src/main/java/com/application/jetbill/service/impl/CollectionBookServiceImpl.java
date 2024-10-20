package com.application.jetbill.service.impl;

import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.model.entity.Book;
import com.application.jetbill.model.entity.Collection;
import com.application.jetbill.model.entity.CollectionBook;
import com.application.jetbill.repository.CollectionBookRepository;
import com.application.jetbill.repository.CollectionRepository;
import com.application.jetbill.service.CollectionBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor

public class CollectionBookServiceImpl implements CollectionBookService {
    private final CollectionBookRepository collectionBookRepository;
    private final CollectionRepository collectionRepository;


    @Override
    @Transactional
    public CollectionBook addBookToCollection(Integer bookId, Integer collectionId) {
        LocalDateTime addedDate = LocalDateTime.now();
        collectionBookRepository.addBookToCollection(bookId, collectionId, addedDate);
        CollectionBook collectionBook = new CollectionBook();
        collectionBook.setBook(bookId);
        collectionBook.setCollection(collectionId);
        collectionBook.setAddedDate(addedDate);

        return collectionBook;
    }

    @Override
    @Transactional
    public void removeBookFromCollection(Integer bookId, Integer collectionId) {
        collectionBookRepository.deleteByBookAndCollection(bookId, collectionId);
    }

    @Override
    public List<Book> getBooksInCollection(Integer collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        return collectionBookRepository.findBooksByCollection(collection);
    }
}
