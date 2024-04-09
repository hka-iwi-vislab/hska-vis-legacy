package com.hka.verteiltesysteme.dto;

public record CreateProductDto(String name, String description, double price, long categoryId) {
}
