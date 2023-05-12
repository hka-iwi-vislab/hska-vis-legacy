package com.categories;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
public class CategoriesController {

    @GetMapping("/categories")
    public String sayCategories() throws Exception{

        URL url = new URL("http://products-service:8081/products");

    // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // This line makes the request
        InputStream responseStream = connection.getInputStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String apod = s.hasNext() ? s.next() : "xxx/";

        return "Fetched from Rest:" + apod;
    }

}
