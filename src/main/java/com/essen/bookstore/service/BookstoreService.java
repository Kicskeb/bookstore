package com.essen.bookstore.service;

import com.essen.bookstore.model.Book;
import com.essen.bookstore.model.Bookstore;
import com.essen.bookstore.respository.BookstoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    @Transactional
    public void removeBookFomInventory(Book book) {
        bookstoreRepository.findAll()
                .forEach(bookstore -> {
                    bookstore.getInventory().remove(book);
                    bookstoreRepository.save(bookstore);
                });
    }

    public void save(Bookstore bookstore) {
        bookstoreRepository.save(bookstore);
    }

    public List<Bookstore> findAll() {
        return bookstoreRepository.findAll();
    }

    public void deleteBookstore(Long id) {
        var bookstore = bookstoreRepository.findById(id).orElseThrow(() -> new RuntimeException("Bookstore does not exist"));
        bookstoreRepository.delete(bookstore);
    }

    public void updateBookstore(Long id, String location, Double priceModifier, Double moneyInCashRegister) {
        if (Stream.of(location, priceModifier, moneyInCashRegister).allMatch(Objects::isNull))
            throw new RuntimeException("Not changed any attribute");

        var bookstore = bookstoreRepository.findById(id).orElseThrow(() -> new RuntimeException("Bookstore does not exist"));
        if (location != null) bookstore.setLocation(location);
        if (priceModifier != null) bookstore.setPriceModifier(priceModifier);
        if (moneyInCashRegister != null) bookstore.setMoneyInCashRegister(moneyInCashRegister);
        bookstoreRepository.save(bookstore);
    }
}
