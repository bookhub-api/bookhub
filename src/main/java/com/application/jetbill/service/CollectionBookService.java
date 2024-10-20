package com.application.jetbill.service;

import com.application.jetbill.model.entity.Book;
import com.application.jetbill.model.entity.CollectionBook;

import java.util.List;

public interface CollectionBookService {
    CollectionBook addBookToCollection(Integer bookId, Integer collectionId);
    void removeBookFromCollection(Integer bookId, Integer collectionId);
    //List<CollectionBook> getBooksInCollection(Integer collectionId);
    List<Book> getBooksInCollection(Integer collectionId);

}