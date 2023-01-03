package com.kapusta;

import com.kapusta.config.RestBootConfig;
import com.kapusta.context.ContextCreationException;
import com.kapusta.context.ExecutionContext;
import com.kapusta.context.RestBootContext;
import com.kapusta.http.ExecutionHandlersProducer;
import com.kapusta.http.ExecutionHttpHandler;
import com.kapusta.http.HttpServerContext;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Slf4j
public class RestBoot {

    private final RestBootConfig config;

    public void run(){
        log.info("Received signal to start application");
        try {
            Set<ExecutionContext> executionContexts = new RestBootContext(config).createContext();
            Map<String, ExecutionHttpHandler> handlers = new ExecutionHandlersProducer(executionContexts).produce();
            log.info("Starting server");
            new HttpServerContext(config, handlers).run();
        } catch (ContextCreationException e) {
            log.error("Error occurred during context creation", e);
        }

    }

}
