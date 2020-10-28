package io.github.kimmking.netty.server.homework;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class OKHttpDemo {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("http://localhost:8801").build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (response.isSuccessful()) {
                System.out.println("success:" + (body == null ? "" : body.string()));
            } else {
                System.err.println("error,statusCode=" + response.code() + ",body=" + (body == null ? "" : body.string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
