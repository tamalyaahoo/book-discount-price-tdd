package com.bnpp.kata.bookdiscount.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BookDiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/v1/book/price/calculate → returns 200 OK and correct price for valid input")
    void testCalculatePriceEndpointSuccess() throws Exception {
        String requestJson = """
        {
          "bookList": [
            { "title": "Clean Code",      "quantity": 1 },
            { "title": "The Clean Coder", "quantity": 1 }
          ]
        }
        """;
        mockMvc.perform(post("/api/v1/book/price/calculate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice",is(95.0)));

    }

    @Test
    @DisplayName("POST /api/v1/book/price/calculate → returns 400 Bad Request when 'items' field is missing")
    void testCalculatePriceEndpointValidationError() throws Exception {
        String requestJson = """
        {
          "unknownField": "invalid"
        }
        """;
        // missing items field
        mockMvc.perform(post("/api/v1/book/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/book/price/calculate → returns 400 Bad Request when quantity is negative")
    void testCalculatePriceEndpointNegativeQuantity() throws Exception {
        String requestJson = """
        {
          "bookList": [
            { "title": "Clean Code", "quantity": -1 }
          ]
        }
        """;
        mockMvc.perform(post("/api/v1/book/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

}