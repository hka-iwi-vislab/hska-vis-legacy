package com.products;

import com.products.database.Product;
import com.products.database.ProductDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductManagerImpl implements ProductManager {
    private ProductDAO productDAO;

    public ProductManagerImpl() {
        productDAO = new ProductDAO();
    }

    @GetMapping("")
    public List<Product> getProducts() {
        return productDAO.getObjectList();
    }


    @GetMapping("/search")
    public List<Product> getProductsForSearchValues(@RequestParam String searchValue,
                                                    @RequestParam Double searchMinPrice, @RequestParam Double searchMaxPrice) {
        return new ProductDAO().getProductListByCriteria(searchValue, searchMinPrice, searchMaxPrice);
    }

    @GetMapping("/id/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productDAO.getObjectById(productId);
    }

    @GetMapping("/name/{productName}")
    public Product getProductByName(@PathVariable String productName) {
        return productDAO.getObjectByName(productName);
    }

    @PostMapping("")
    public int addProduct(@RequestBody Product product) {
        int productId = -1;

        if (product.getDetails() == null) {
            product = new Product(product.getName(), product.getPrice(), product.getCategoryId());
        } else {
            product = new Product(product.getName(), product.getPrice(), product.getCategoryId(), product.getDetails());
        }

        productDAO.saveObject(product);
        productId = product.getId();

        return productId;
    }

    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable int productId) {
        productDAO.deleteById(productId);
    }

    @DeleteMapping("/by-category/{categoryId}")
    public boolean deleteProductsByCategoryId(@PathVariable int categoryId) {
        // TODO Delete all Products with CategoryId
        return false;
    }

}
