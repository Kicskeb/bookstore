package com.essen.bookstore.shell;

import com.essen.bookstore.model.Book;
import com.essen.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Book related commands")
@RequiredArgsConstructor
public class BookHandler {

    private final BookService bookService;

    @ShellMethod(key = "create book", value = "Create a book")
    void createBook(String title, String author, String publisher, Double price) {
        bookService.save(Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .build());
    }

    @ShellMethod(key = "list books", value = "List books")
    public String listBooks() {
        return bookService.findAll().stream()
                .map(book -> "ID: %d, Publisher: %s, Author: %s, Price: %f FT".formatted(
                        book.getId(),
                        book.getPublisher(),
                        book.getAuthor(),
                        book.getPrice()
                )).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = "delete book", value = "Delete book")
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    @ShellMethod(key = "update book", value = "Update a book")
    public void updateBook(Long id,
                           @ShellOption(defaultValue = ShellOption.NULL)String publisher,
                           @ShellOption(defaultValue = ShellOption.NULL)String title,
                           @ShellOption(defaultValue = ShellOption.NULL)String author,
                           @ShellOption(defaultValue = ShellOption.NULL)Double price) {
        bookService.updateBook(id, publisher, title, author, price);
    }

    @ShellMethod(key = "find price", value = "Find the books price")
    public String findPrices(Long id) {
        return bookService.findPrices(id);
    }
}
