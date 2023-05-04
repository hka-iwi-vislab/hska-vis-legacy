package com.categories;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

    @GetMapping("/categories")
    public String sayCategories(){
        return "I am the categories microservice :)";
    }

}
