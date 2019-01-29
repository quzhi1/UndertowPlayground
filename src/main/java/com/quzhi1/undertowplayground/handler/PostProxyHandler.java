package com.quzhi1.undertowplayground.handler;

import com.google.common.io.CharStreams;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import io.undertow.util.Headers;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostProxyHandler implements HttpHandler {

  private final OkHttpClient okHttpClient;
  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  public PostProxyHandler() {
    this.okHttpClient = setupOkHttpClient();
  }

  @Override
  public void handleRequest(final HttpServerExchange exchange) {
    try (final Reader reader = new InputStreamReader(exchange.getInputStream())) {
      // Read post body and url
      String text = CharStreams.toString(reader);
      String url = exchange.getQueryParameters().get("url").getFirst();

      // Read proxy content type header
      String contentType = exchange.getRequestHeaders().get(Headers.CONTENT_TYPE).toString();

      // Build request
      RequestBody postBody = RequestBody.create(JSON, text);
      Request request = new Request.Builder()
          .url(url)
          .post(postBody)
          .addHeader(Headers.CONTENT_TYPE.toString(), contentType)
          .build();

      // Do real call
      Response response = okHttpClient.newCall(request).execute();
      String responseStr = response.body() == null ? "" : response.body().string();
      String responseHeader = response.header(Headers.CONTENT_TYPE_STRING);

      // Construct response
      exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, responseHeader);
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

