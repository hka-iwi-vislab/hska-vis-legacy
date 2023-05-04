package com.products;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

    @GetMapping("/products")
    public String categories(){
        return "I am the awesome products microservice :=)";
    }


}
