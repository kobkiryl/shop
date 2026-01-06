package com.example.shop;

import org.springframework.data.annotation.Id;

public record ShopCard(@Id Long id, Double amount, String owner) {
}
