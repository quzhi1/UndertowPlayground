package com.quzhi1.undertowplayground.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class HelloworldHandler implements HttpHandler {

  @Override
  public void handleRequest(final HttpServerExchange exchange) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
    exchange.getResponseSender().send("Hello World");
  }
}

