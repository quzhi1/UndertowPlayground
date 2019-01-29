package com.quzhi1.undertowplayground.handler;

import com.google.common.io.CharStreams;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class PostEcho implements HttpHandler {

  @Override
  @SuppressWarnings("UnstableApiUsage")
  public void handleRequest(final HttpServerExchange exchange) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
        exchange.getRequestHeaders().get(Headers.CONTENT_TYPE).toString());
    try (final Reader reader = new InputStreamReader(exchange.getInputStream())) {
      String text = CharStreams.toString(reader);
      exchange.getResponseSender().send(text);
    } catch (IOException e) {
      System.out.println("Caught IOException: " + e);
    }

  }
}

