package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {


    private final HttpDao httpDao = new HttpDao("http://reverse-proxy:5000/product");


    @Override
    public List<Product> getProducts() {
        try {
            return httpDao.getList("/products");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(int id) {
        try {
            return httpDao.get("/product/" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductByName(String name) {
        return null;
    }


    @Override
    public int addProduct(String name, double price, int categoryId, String details) {
        try {
            return httpDao.post("/product", new Gson().toJson(new UpsertProduct(details, name, price, categoryId)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {
        return null;
    }

    @Override
    public boolean deleteProductsByCategoryId(int categoryId) {
        return false;
    }

    @Override
    public void deleteProductById(int id) {
        try {
            httpDao.delete("/product/" + id);
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
