package com.kapusta.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Executor;

class HttpServerRunner {

    private final int port;
    private final String host;
    private final Executor threadPool;

    private HttpServer httpServer;

    HttpServerRunner(int port, String host, Executor threadPool) {
        this.port = port;
        this.host = host;
        this.threadPool = threadPool;
    }

    void create(Map<String, HttpHandler> contexts) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
        for(Map.Entry<String, HttpHandler> context: contexts.entrySet()){
            this.httpServer.createContext(context.getKey(), context.getValue());
        }
        this.httpServer.setExecutor(threadPool);
        this.httpServer.start();
    }
}