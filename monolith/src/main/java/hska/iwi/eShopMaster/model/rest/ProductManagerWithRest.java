package hska.iwi.eShopMaster.model.rest;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.util.List;

public class ProductManagerWithRest implements ProductManager {

    private RestHelper restHelper;
    private Gson gson;

    public ProductManagerWithRest() {
        restHelper = new RestHelper();
        gson = new Gson();
    }

    public List<Product> getProducts() {
        return null;
    }

    public Product getProductById(int id) {
        return null;
    }

    public Product getProductByName(String name) {
        return null;
    }

    public int addProduct(String name, double price, int categoryId, String details) {
        return 0;
    }

    public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {
        return null;
    }

    public boolean deleteProductsByCategoryId(int categoryId) {
        return false;
    }

    public void deleteProductById(int id) {

    }
}
