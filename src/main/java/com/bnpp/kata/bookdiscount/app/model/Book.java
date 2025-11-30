package com.bnpp.kata.bookdiscount.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {
    private String title;
    private int quantity;
}