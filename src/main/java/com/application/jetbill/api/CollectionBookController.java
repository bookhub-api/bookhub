package com.application.jetbill.api;



import com.application.jetbill.model.entity.Book;
import com.application.jetbill.model.entity.CollectionBook;
import com.application.jetbill.service.CollectionBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/collections-books")
@RequiredArgsConstructor
public class CollectionBookController {

    private final CollectionBookService collectionBookService;

    @PostMapping("/{collectionId}/add-book")
    public ResponseEntity<CollectionBook> addBookToCollection(@PathVariable Integer collectionId,
                                                              @RequestParam Integer bookId) {
        CollectionBook collectionBook = collectionBookService.addBookToCollection(bookId, collectionId);
        return new ResponseEntity<>(collectionBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{collectionId}/remove-book/{bookId}")
    public ResponseEntity<Void> removeBookFromCollection(@PathVariable Integer collectionId,
                                                         @PathVariable Integer bookId) {
        collectionBookService.removeBookFromCollection(bookId, collectionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{collectionId}/books")
    public ResponseEntity<List<Book>> getBooksInCollection(@PathVariable Integer collectionId) {
        List<Book> books = collectionBookService.getBooksInCollection(collectionId);
        return ResponseEntity.ok(books);
    }
}