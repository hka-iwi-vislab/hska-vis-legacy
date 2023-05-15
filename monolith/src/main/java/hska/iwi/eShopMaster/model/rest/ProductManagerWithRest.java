package hska.iwi.eShopMaster.model.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.HttpUrl;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductManagerWithRest implements ProductManager {

    private RestHelper restHelper;
    private Gson gson;

    public ProductManagerWithRest() {
        restHelper = new RestHelper();
        gson = new Gson();
    }

    public List<Product> getProducts() {
        String url = RestHelper.productServiceUrl;

        try
        {
            String productsString = restHelper.doGetRequest(url);
            Type productListType = new TypeToken<ArrayList<Product>>(){}.getType();
            List<Product> list = gson.fromJson(productsString, productListType);

            return list;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public Product getProductById(int id) {
        String url = RestHelper.productServiceUrl + "/id/" + id;

        try
        {
            String productsString = restHelper.doGetRequest(url);
            return gson.fromJson(productsString, Product.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public Product getProductByName(String name) {
        String url = RestHelper.productServiceUrl + "/name/" + name;

        try
        {
            String productsString = restHelper.doGetRequest(url);
            return gson.fromJson(productsString, Product.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public int addProduct(String name, double price, int categoryId, String details) {
        String jsonProduct = gson.toJson(new Product(name, price, categoryId, details));

        try
        {
            String productIdResult = restHelper.doPostRequest(RestHelper.productServiceUrl, jsonProduct);
            return gson.fromJson(productIdResult, int.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(RestHelper.productServiceUrl + "/search").newBuilder();
        urlBuilder.addQueryParameter("searchValue", searchValue);
        urlBuilder.addQueryParameter("searchMinPrice", searchMinPrice != null ? searchMinPrice.toString() : "-1");
        urlBuilder.addQueryParameter("searchMaxPrice", searchMaxPrice != null ? searchMaxPrice.toString() : "-1");

        try
        {
            System.out.println(urlBuilder.build().toString());
            String productsString = restHelper.doGetRequest(urlBuilder.build().toString());
            Type productListType = new TypeToken<ArrayList<Product>>(){}.getType();
            return gson.fromJson(productsString, productListType);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public boolean deleteProductsByCategoryId(int categoryId) {
        String url = RestHelper.productServiceUrl + "/by-category/" + categoryId;
        try
        {
            restHelper.doDeleteRequest(url);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteProductById(int id) {
        String url = RestHelper.productServiceUrl + "/" + id;
        try
        {
            restHelper.doDeleteRequest(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
