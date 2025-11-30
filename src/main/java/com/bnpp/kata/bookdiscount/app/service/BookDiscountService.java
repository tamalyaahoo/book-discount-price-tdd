package com.bnpp.kata.bookdiscount.app.service;

import com.bnpp.kata.bookdiscount.app.exception.InvalidBookException;
import com.bnpp.kata.bookdiscount.app.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookDiscountService {

    private static final double BOOK_PRICE = 50.0;
    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            1, 0.00,
            2, 0.05,
            3, 0.10,
            4, 0.20,
            5, 0.25
    );

    public double calculatePrice(List<Book> bookList) {
        this.validateBasket(bookList);
        int bookCount = bookList.size();
        double discount = DISCOUNTS.getOrDefault(bookCount, 0.0);
        double totalPrice = bookCount * BOOK_PRICE * (1-discount);
        return totalPrice;
    }

    private void validateBasket(List<Book> items) {
        requireNonNullList(items);
        requireNonEmptyList(items);
        validateEachBookItem(items);
        ensureAtLeastOnePositiveQuantity(items);
    }

    private void requireNonNullList(List<Book> items) {
        Optional.ofNullable(items)
                .orElseThrow(() -> new InvalidBookException("Basket must not be null"));
    }

    private void requireNonEmptyList(List<Book> items) {
        Optional.of(items)
                .filter(bookList -> !bookList.isEmpty())
                .orElseThrow(() -> new InvalidBookException("Basket must contain at least one entry"));
    }

    private void validateEachBookItem(List<Book> items) {
        items.forEach(item -> {
            String title = Optional.ofNullable(item.getTitle())
                    .map(String::trim)
                    .filter(bookTitle -> !bookTitle.isEmpty())
                    .orElseThrow(() ->
                            new InvalidBookException("Book title must not be null or empty"));

            Integer qty = Optional.ofNullable(item.getQuantity())
                    .orElseThrow(() ->
                            new InvalidBookException("Quantity for book '%s' must not be null".formatted(title)));

            Optional.of(qty)
                    .filter(quantity -> quantity >= 0)
                    .orElseThrow(() ->
                            new InvalidBookException("Quantity for book '%s' must not be negative".formatted(title)));
        });
    }

    private void ensureAtLeastOnePositiveQuantity(List<Book> items) {
        items.stream()
                .map(Book::getQuantity)
                .filter(Objects::nonNull)
                .filter( count-> count > 0)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidBookException("Basket must contain at least one book with quantity > 0"));
    }
}
