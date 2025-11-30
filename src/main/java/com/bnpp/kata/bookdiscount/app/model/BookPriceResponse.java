package com.bnpp.kata.bookdiscount.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookPriceResponse {
    private List<Book> books;
    private double totalPrice;
}
