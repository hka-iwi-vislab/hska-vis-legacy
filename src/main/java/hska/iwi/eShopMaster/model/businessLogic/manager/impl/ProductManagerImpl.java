package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductManagerImpl  implements ProductManager {
//    private final HttpDao httpDao = new HttpDao("http://reverse-proxy:5000/product");
    private final HttpDao httpDao = new HttpDao("http://product.default.svc.cluster.local:8081");

    @Override
    public List<Product> getProducts() {
        try {
            return httpDao.getList("/products", Product.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(int id) {
        try {
            return httpDao.get("/product/" + id, Product.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductByName(String name) {
        return getProducts().stream().filter(p -> p.getName().equals(name)).findFirst().orElseGet(null);
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
