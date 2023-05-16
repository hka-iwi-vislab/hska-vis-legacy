package com.products;

import com.products.database.Product;
import com.products.database.ProductDAO;
import com.products.rest.RestHelper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductManagerImpl implements ProductManager {
    private ProductDAO productDAO;
    private RestHelper restHelper;

    public ProductManagerImpl() {
        productDAO = new ProductDAO();
        restHelper = new RestHelper();
    }

    @GetMapping("")
    public List<Product> getProducts() {
        return productDAO.getObjectList();
    }


    @GetMapping("/search")
    public List<Product> getProductsForSearchValues(@RequestParam String searchValue,
                                                    @RequestParam Double searchMinPrice, @RequestParam Double searchMaxPrice) {
        System.out.println(searchValue);
        return productDAO.getProductListByCriteria(searchValue, searchMinPrice, searchMaxPrice);
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
        String url = RestHelper.categoryServiceUrl + "/exists/" + product.getCategoryId();
        boolean exists = false;

        try
        {
            String categoryExistString = restHelper.doGetRequest(url);
            exists = categoryExistString.equals("true");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        int productId = -1;

        if(exists)
        {
            if (product.getDetails() == null) {
                product = new Product(product.getName(), product.getPrice(), product.getCategoryId());
            } else {
                product = new Product(product.getName(), product.getPrice(), product.getCategoryId(), product.getDetails());
            }

            productDAO.saveObject(product);
            productId = product.getId();
        }

        return productId;
    }

    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable int productId) {
        productDAO.deleteById(productId);
    }

    @DeleteMapping("/by-category/{categoryId}")
    public boolean deleteProductsByCategoryId(@PathVariable int categoryId) {
        productDAO.deleteByCategoryId(categoryId);
        return true;
    }

}
