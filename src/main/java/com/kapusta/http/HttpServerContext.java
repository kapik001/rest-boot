package com.kapusta.http;

import com.kapusta.config.RestBootConfig;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServerContext {
    private final RestBootConfig restBootConfig;
    private final Map<String, ExecutionHttpHandler> contexts;

    private HttpServerRunner httpServerRunner;

    public HttpServerContext(RestBootConfig restBootConfig, Map<String, ExecutionHttpHandler> contexts){
        this.restBootConfig = restBootConfig;
        this.contexts = contexts;
    }

    public void run(){
        Executor threadPoolExecutor = Executors.newFixedThreadPool(restBootConfig.getThreadPoolSize());
        this.httpServerRunner = new HttpServerRunner(restBootConfig.getPort(), restBootConfig.getHostName(), threadPoolExecutor);
        try {
            this.httpServerRunner.create(contexts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
