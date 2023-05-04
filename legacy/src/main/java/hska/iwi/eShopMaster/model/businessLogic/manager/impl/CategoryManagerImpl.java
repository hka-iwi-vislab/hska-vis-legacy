package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.CategoryDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import reactor.core.publisher.Mono;

import java.io.Console;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

// import javax.ws.rs.client.Client;
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.core.GenericType;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class CategoryManagerImpl implements CategoryManager {

	private static final String REST_URI = "http://host.docker.internal:8081/api/v1/categories";

	private WebClient webClient;

	public CategoryManagerImpl() {

		webClient = WebClient.create(REST_URI);

	}

	public List<Category> getCategories() {
		Mono<List<Category>> categories = webClient.get()
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Category>>() {
				});

		return categories.block();

	}

	public Category getCategory(int id) {
		Mono<Category> response = webClient.get().uri("/{id}", id).retrieve().bodyToMono(Category.class);
		return response.block();
	}

	public Category getCategoryByName(String name) {
		Mono<Category> response = webClient.get().uri("/{name}", name).retrieve().bodyToMono(Category.class);
		return response.block();
		// return helper.getObjectByName(name);
	}

	public void addCategory(String name) {
		Mono<ResponseEntity<Category>> newCategory = webClient.post().contentType(MediaType.TEXT_PLAIN)
				.body(BodyInserters.fromValue(name)).retrieve().toEntity(Category.class);

		newCategory.block();
	}

	public void delCategory(Category cat) {

		delCategoryById(cat.getId());

	}

	public void delCategoryById(int id) {
		Mono<Void> response = webClient.delete().uri("/{id}", id).retrieve().bodyToMono(Void.class);
		response.block();
	}
}
