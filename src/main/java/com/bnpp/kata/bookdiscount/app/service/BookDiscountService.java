package com.bnpp.kata.bookdiscount.app.service;

import com.bnpp.kata.bookdiscount.app.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        int bookCount = bookList.size();
        double discount = DISCOUNTS.getOrDefault(bookCount, 0.0);
        double totalPrice = bookCount * BOOK_PRICE * (1-discount);
        return totalPrice;
    }
}
