package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class ProductManagerImpl implements ProductManager {

	private static final String PRODUCTS_API_BASE_PATH = "http://host.docker.internal:8081/api/v1/products";
	private static final String CATEGORIES_API_BASE_PATH = "http://host.docker.internal:8081/api/v1/categories";

	private WebClient productsWebClient;
	private WebClient categoriesWebClient;

	private ProductDAO helper;

	public ProductManagerImpl() {
		productsWebClient = WebClient.create(PRODUCTS_API_BASE_PATH);
		categoriesWebClient = WebClient.create(CATEGORIES_API_BASE_PATH);
		helper = new ProductDAO();
	}

	public List<Product> getProducts() {
		Mono<List<Product>> products = productsWebClient.get().retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Product>>() {
				});
		return products.block();
	}

	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {

		Mono<List<Product>> products = productsWebClient.get()
			.uri(uriBuilder -> uriBuilder
				.queryParam("minPrice", searchMinPrice)
				.queryParam("maxPrice", searchMaxPrice)
				.queryParam("details", searchDescription)
				.build())
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<List<Product>>() {
			});
		return products.block();
	}

	public Product getProductById(int id) {
		Mono<Product> response = productsWebClient.get().uri("/{id}", id).retrieve().bodyToMono(Product.class);
		return response.block();
	}

	public Product getProductByName(String name) {
		Mono<Product> response = productsWebClient.get().uri("/name/{name}", name).retrieve().bodyToMono(Product.class);
		return response.block();
	}

	public int addProduct(String name, double price, int categoryId, String details) {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("price", price);
		json.put("categoryId", categoryId);
		json.put("details", details);

		// Send the JSON object as the request body
		ResponseEntity<Product> response = productsWebClient.post()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(json.toString()))
				.retrieve()
				.toEntity(Product.class)
				.block();

		System.out.println(response);
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			
			Product newProduct = response.getBody();

			if (newProduct != null) {
				return newProduct.getId();
			}
		}

		return -1;
	}

	public void deleteProductById(int id) {
		Mono<Void> response = productsWebClient.delete().uri("/{id}", id).retrieve().bodyToMono(Void.class);
		response.block();
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		Mono<Void> response = categoriesWebClient.delete().uri("/{id}", categoryId).retrieve().bodyToMono(Void.class);
		response.block();
		return true;
	}

}
