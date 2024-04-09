package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.dto.CreateProductDto;
import com.hka.verteiltesysteme.models.Product;
import com.hka.verteiltesysteme.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepo productRepo;

    @PostMapping
    public ResponseEntity<String> CreateProduct(CreateProductDto createProductDto) {
        try {
            var product = Product.builder().name(createProductDto.name()).build();
            productRepo.save(product);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> DeleteProduct(int id) {
        try {
            productRepo.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping
    public ResponseEntity<Product> GetProduct(int id) {
        try {
            return ResponseEntity.ok(productRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Product>> GetProducts() {
        try {
            return ResponseEntity.ok(productRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
