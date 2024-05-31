package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import lombok.RequiredArgsConstructor;

import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {


	private final HttpDao httpDao = new HttpDao("http://prdoucts:8081");


	@Override
	public List<Product> getProducts() {
        try {
            return httpDao.getList("/products");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public Product getProductById(int id) {
		try {
			return httpDao.get("/product/"+id);
		} catch (URISyntaxException e) {
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
			return httpDao.post("/product", new ObjectMapper().writeValueAsString(new UpsertProduct(details, name, price, categoryId)));
		} catch (URISyntaxException | JsonProcessingException e) {
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

	}

	record UpsertProduct(String description, String name, Double price, int categoryId){}
}
