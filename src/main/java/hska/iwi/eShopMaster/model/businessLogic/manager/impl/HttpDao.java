package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HttpDao {
    private final String url;

    public <T> T post(String path, String requestBody, Class<T> clazz) throws IOException {
        String uri = url + path;

        OkHttpClient client = new OkHttpClient();
        System.out.println("Sending request to: " + uri);
        System.out.println("Request: " + requestBody);

        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();
        return send(client, request, clazz);
    }

    public <T> T get(String path, Class<T> clazz) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(this.url + path)
                .get()
                .build();
        return send(client, request, clazz);
    }

    public <T> T delete(String path, Class<T> clazz) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.url + path)
                .delete()
                .build();
        return send(client, request, clazz);
    }

    public <T> List<T> getList(String path) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.url + path)
                .get()
                .build();
        return sendList(client, request);
    }

    private <T> T send(OkHttpClient client, Request request, Class<T> clazz) throws IOException {
        Response response = client.newCall(request).execute();

        if(response.code() == 404) return null;
        else if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String s = response.body().string();
        System.out.println(s);
        System.out.println(response.body());
        T result = new Gson().fromJson(s, clazz);

        System.out.println(result);
        return result;
    }

    private <T> List<T> sendList(OkHttpClient client, Request request) throws IOException {
        Response response = client.newCall(request).execute();

        if(response.code() == 404) return null;
        else if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        String s = response.body().string();
        System.out.println(s);
        System.out.println(response.body());
        return new Gson().fromJson(s, type);
    }
}
