package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.models.Product;
import com.hka.verteiltesysteme.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final ProductRepo productRepo;

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable int id) {
        List<Product> delete = productRepo.findAll().stream().filter(p -> p.getCategoryId() == id).toList();
        productRepo.deleteAll(delete);
        return ResponseEntity.status(200).build();
    }
}
