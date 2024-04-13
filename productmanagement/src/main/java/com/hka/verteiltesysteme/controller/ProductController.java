package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.dto.CreateProductDto;
import com.hka.verteiltesysteme.models.Category;
import com.hka.verteiltesysteme.models.Product;
import com.hka.verteiltesysteme.repositories.CategoryRepo;
import com.hka.verteiltesysteme.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @PostMapping("/product")
    public ResponseEntity<String> CreateProduct(@RequestBody CreateProductDto createProductDto) {
        try {
            Category category = categoryRepo.getById((int) createProductDto.categoryId());
            var product = Product.builder().name(createProductDto.name())
                    .category(category)
                    .price(createProductDto.price())
                    .details(createProductDto.description()).build();
            productRepo.save(product);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }

    @DeleteMapping("/product")
    public ResponseEntity<String> DeleteProduct(int id) {
        try {
            productRepo.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping("/product")
    public ResponseEntity<Product> GetProduct(int id) {
        try {
            return ResponseEntity.ok(productRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/product/all")
    public ResponseEntity<Iterable<Product>> GetProducts() {
        try {
            return ResponseEntity.ok(productRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
