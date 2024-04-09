package com.hka.verteiltesysteme.dto;

public record UpsertProductDto(String name, String description, double price, int categoryId) {
}
