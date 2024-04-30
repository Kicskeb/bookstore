package com.essen.bookstore.shell;

import com.essen.bookstore.model.Book;
import com.essen.bookstore.model.Bookstore;
import com.essen.bookstore.service.BookService;
import com.essen.bookstore.service.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Bookstore related commands")
@RequiredArgsConstructor
public class BookstoreHandler {
    private final BookstoreService bookstoreService;

    @ShellMethod(key = "create bookstore", value = "Create a bookstore")
    void createBookstore(String location, Double priceModifier, Double moneyInCashRegister) {
        bookstoreService.save(Bookstore.builder()
                        .location(location)
                        .priceModifier(priceModifier)
                        .moneyInCashRegister(moneyInCashRegister)
                        .build());
    }

    @ShellMethod(key = "list bookstores", value = "List bookstores")
    public String listBookstore() {
        return bookstoreService.findAll().stream()
                .map(bookstore -> "ID: %d, Location: %s, Price modifier: %f, Money in cash register: %f FT".formatted(
                        bookstore.getId(),
                        bookstore.getLocation(),
                        bookstore.getPriceModifier(),
                        bookstore.getMoneyInCashRegister()
                )).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "delete bookstore", value = "Delete bookstore")
    public void deleteBookstore(Long id) {
        bookstoreService.deleteBookstore(id);
    }

    @ShellMethod(key = "update bookstore", value = "Update a bookstore")
    public void updateBookstore(Long id,
                           @ShellOption(defaultValue = ShellOption.NULL)String location,
                           @ShellOption(defaultValue = ShellOption.NULL)Double priceModifier,
                           @ShellOption(defaultValue = ShellOption.NULL)Double moneyInCashRegister) {
        bookstoreService.updateBookstore(id, location, priceModifier, moneyInCashRegister);
    }

    @ShellMethod(key = "get stocks", value = "Get the full stock of a bookstore")
    public Map<Book, Integer> getStocks(Long id) {
        return bookstoreService.getStocks(id);
    }

    @ShellMethod(key = "change stocks", value = "Change the stock to the bookstore inventory")
    public void changeStock(Long bookstoreId, Long bookId, Integer amount) {
        bookstoreService.changeStock(bookstoreId, bookId, amount);
    }
}
