package com.quzhi1.undertowplayground;

import com.quzhi1.undertowplayground.handler.GetProxyHandler;
import com.quzhi1.undertowplayground.handler.HelloworldHandler;
import com.quzhi1.undertowplayground.handler.PostEcho;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;

public class Main {

  public static void main(final String[] args) {
    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(Handlers.path()
            .addPrefixPath("/", Handlers.routing()
                .get("hello", new HelloworldHandler())
                .post("echo", new BlockingHandler(new PostEcho()))
                .get("proxy", new GetProxyHandler())
            )
        ).build();
    server.start();
  }
}
