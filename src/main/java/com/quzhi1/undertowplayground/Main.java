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
      // Create load balancer
//      LoadBalancingProxyClient loadBalancer = new LoadBalancingProxyClient()
//          .addHost(new URI(HOST))
////          .addHost(new URI(HOST), null,
////              new UndertowXnioSsl(Xnio.getInstance(), OptionMap.EMPTY, sslContext))
//          .setConnectionsPerThread(20);
//      ProxyHandler proxyHandler = new ProxyHandler(loadBalancer, 0, new SimpleErrorPageHandler());

      // Start server
//      String host = InetAddress.getLocalHost().getHostAddress();
      String host = "localhost";
      Undertow server = Undertow.builder()
          .addHttpListener(8080, host)
          .setHandler(Handlers.path()
              .addPrefixPath("/", Handlers.routing()
                  .get("hello", new HelloworldHandler())
                  .post("echo", new BlockingHandler(new PostEcho()))
                  .get("proxy/get", new GetProxyHandler())
                  .post("proxy/post/reinvent", new BlockingHandler(new PostProxyHandler()))
//                  .post("proxy/post", proxyHandler)
              )
          ).build();
      System.out.println("Starting server: " + host + ":8080");
      server.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
