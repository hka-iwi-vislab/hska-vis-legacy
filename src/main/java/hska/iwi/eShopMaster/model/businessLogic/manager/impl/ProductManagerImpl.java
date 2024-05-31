package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.ProductDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {
    //    private final HttpDao httpDao = new HttpDao("http://reverse-proxy:5000/product");
    private final HttpDao httpDao = new HttpDao("http://product.default.svc.cluster.local:8081");
    private final CategoryManager categoryManager = new CategoryManagerImpl();

    @Override
    public List<Product> getProducts() {
        try {
            List<ProductDto> products = httpDao.getList("/products", ProductDto[].class);
            List<Category> categories = categoryManager.getCategories();

            return products.stream().map(product -> {
                return new Product(product.getId(), product.getName(), product.getPrice(), categories.stream().filter(category -> category.getId() == product.getCategoryId()).findFirst().orElse(null), product.getDetails());
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(int id) {
        try {
            ProductDto productDto = httpDao.get("/product/" + id, ProductDto.class);
            Category category = categoryManager.getCategory(productDto.getCategoryId());
            return new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), category, productDto.getDetails());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductByName(String name) {
        return getProducts().stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }


    @Override
    public int addProduct(String name, double price, int categoryId, String details) {
        try {
            httpDao.post("/product", new Gson().toJson(new UpsertProduct(details, name, price, categoryId)), Object.class);
            return 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {
        return getProducts().stream()
                .filter(p -> searchValue == null || p.getName().contains(searchValue))
                .filter(p -> searchMinPrice == null || p.getPrice() >= searchMinPrice)
                .filter(p -> searchMaxPrice == null || p.getPrice() <= searchMaxPrice)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteProductsByCategoryId(int categoryId) {
        getProducts().stream().filter(p -> p.getCategory().getId() == categoryId).map(Product::getId).forEach(this::deleteProductById);
        return true;
    }

    @Override
    public void deleteProductById(int id) {
        try {
            httpDao.delete("/product/" + id, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    class UpsertProduct {
        private final String description;
        private final String name;
        private final Double price;
        private final int categoryId;
    }
}
