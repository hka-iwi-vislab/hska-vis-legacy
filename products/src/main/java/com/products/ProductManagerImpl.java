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
    public List<Product> getProductsForSearchValues(@RequestParam String searchDescription,
                                                    @RequestParam Double searchMinPrice, @RequestParam Double searchMaxPrice) {
        return new ProductDAO().getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice);
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

    //public int addProduct(String name, double price, int categoryId, String details) {
        int productId = -1;

        CategoryManager categoryManager = new CategoryManagerImpl();
        Category category = categoryManager.getCategory(categoryId);

        if (category != null) {
            Product product;
            if (details == null) {
                product = new Product(name, price, category);
            } else {
                product = new Product(name, price, category, details);
            }

            productDAO.saveObject(product);
            productId = product.getId();
        }

        return productId;
    }


    public void deleteProductById(int id) {
        productDAO.deleteById(id);
    }

    public boolean deleteProductsByCategoryId(int categoryId) {
        // TODO Auto-generated method stub
        return false;
    }

}
