package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
public class HttpDao {
    private final String url;

    public <T> T post(String path, String requestBody) throws URISyntaxException, IOException {
        String uri = url + path;

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(uri)
                .post(body)
                .build();

        return send(client, request);
    }

    public <T> T get(String path) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.url + path)
                .get()
                .build();
        return send(client, request);
    }

    public <T> List<T> getList(String path) throws URISyntaxException, IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.url + path)
                .get()
                .build();
        return sendList(client, request);
    }

    private <T> T send(OkHttpClient client, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Type type = new TypeToken<T>() {
        }.getType();
        return new Gson().fromJson(response.body().toString(), type);
    }

    private <T> List<T> sendList(OkHttpClient client, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Type type = new TypeToken<List<T>>() {
        }.getType();
        return new Gson().fromJson(response.body().toString(), type);
    }
}
