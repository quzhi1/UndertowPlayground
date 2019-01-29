package com.quzhi1.undertowplayground.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class GetProxyHandler implements HttpHandler {

  private final OkHttpClient okHttpClient;

  public GetProxyHandler() {
    this.okHttpClient = setupOkHttpClient();
  }

  @Override
  public void handleRequest(final HttpServerExchange exchange) {
    Request.Builder builder = new Request.Builder();
    String url = exchange.getQueryParameters().get("url").getFirst();
    builder.url(url);
    Request request = builder.build();
    try {
      Response response = okHttpClient.newCall(request).execute();
      String responseStr = response.body() == null ? "" : response.body().string();
      exchange.getResponseSender().send(responseStr);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private OkHttpClient setupOkHttpClient() {
    return new Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectionPool(new ConnectionPool(100, 30, TimeUnit.MINUTES))
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .build();
  }
}

