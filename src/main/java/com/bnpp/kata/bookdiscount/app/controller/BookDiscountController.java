package com.bnpp.kata.bookdiscount.app.controller;

import com.bnpp.kata.bookdiscount.app.model.BookPriceResponse;
import com.bnpp.kata.bookdiscount.app.model.BookRequest;
import com.bnpp.kata.bookdiscount.app.service.BookDiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book/price")
public class BookDiscountController {

    private BookDiscountService discountService;

    public BookDiscountController(BookDiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<BookPriceResponse> calculatePrice(@RequestBody BookRequest request) {
        discountService.calculatePrice(request.getBookList());
        return null;
    }
}
