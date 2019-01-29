package com.quzhi1.undertowplayground;

import com.quzhi1.undertowplayground.handler.GetProxyHandler;
import com.quzhi1.undertowplayground.handler.HelloworldHandler;
import com.quzhi1.undertowplayground.handler.PostEcho;
import com.quzhi1.undertowplayground.handler.PostProxyHandler;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.error.SimpleErrorPageHandler;
import io.undertow.server.handlers.proxy.LoadBalancingProxyClient;
import io.undertow.server.handlers.proxy.ProxyHandler;
import java.net.InetAddress;
import java.net.URI;

public class Main {

  private final static String HOST = "http://postman-echo.com/post";

  public static void main(final String[] args) {
    try {
      // Start server
      Undertow server = Undertow.builder()
          .addHttpListener(8080, InetAddress.getLocalHost().getHostAddress())
          .setHandler(Handlers.path()
              .addPrefixPath("/", Handlers.routing()
                  .get("hello", new HelloworldHandler())
                  .post("echo", new BlockingHandler(new PostEcho()))
                  .get("proxy/get", new GetProxyHandler())
                  .post("proxy/post/reinvent", new BlockingHandler(new PostProxyHandler()))
              )
          ).build();
      System.out.println("Starting server: " + InetAddress.getLocalHost().getHostAddress() + ":8080");
      server.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
