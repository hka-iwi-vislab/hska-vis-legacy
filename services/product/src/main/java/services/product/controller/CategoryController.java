package services.product.controller;

import java.io.Console;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import services.product.model.CategoryRepository;
import services.product.model.Category;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepositroy;
    
    @GetMapping(path = "")
    public List<Category> getAllCategories(){
        return categoryRepositroy.findAll();
    }

    @PostMapping(value="/test",consumes = "application/json")
    public ResponseEntity<Category> handlePostRequest(@RequestBody String name) {

        if(categoryRepositroy.existsByName(name)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory = categoryRepositroy.save(newCategory);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newCategory.getId()).toUri();
        return ResponseEntity.created(location).body(newCategory);
    }

}
