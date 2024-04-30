package com.essen.bookstore.service;

import com.essen.bookstore.model.Book;
import com.essen.bookstore.model.Bookstore;
import com.essen.bookstore.respository.BookRepository;
import com.essen.bookstore.respository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;
    private final BookstoreService bookstoreService;

    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void deleteBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book does not exist"));
        bookstoreService.removeBookFomInventory(book);
        bookRepository.delete(book);
    }

    public void updateBook(Long id, String publisher, String title, String author, Double price) {
        if (Stream.of(title, author, price).allMatch(Objects::isNull))
            throw new UnsupportedOperationException("Not changed any attribute");

        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book does not exist"));
        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (price != null) book.setPrice(price);
        if (publisher != null) book.setPublisher(publisher);
        bookRepository.save(book);
    }

    public String findPrices(Long id) {
        StringBuilder result = new StringBuilder();
        var book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book does not exist"));
        var bookstores = bookstoreService.findAll();
        for (var bookstore : bookstores) {
            result.append(bookstore.getLocation()).append(": ").append(book.getPrice()*bookstore.getPriceModifier()).append("\n");
        }
        return String.valueOf(result);
//        var book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book does not exist"));
//        var bookstores = bookstoreService.findAll();
//        return bookstores.stream().collect(Collectors.toMap(Bookstore::getLocation, bookstore -> (int) (bookstore.getPriceModifier() * book.getPrice())));
    }
}
