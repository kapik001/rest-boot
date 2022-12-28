package com.kapusta;

import com.kapusta.config.RestBootConfig;
import com.kapusta.context.RestBootContext;
import com.kapusta.http.HttpServerContext;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;

@AllArgsConstructor
@Slf4j
public class RestBoot {

    private final RestBootConfig config;

    public void run(){
        log.info("Received signal to start application");
//        RestBootContext context = new ComponentScanner(config).scan();
        Map<String, HttpHandler> handlers = null;
//        try {
//            handlers = new ContextProducer().produceContext(context);
//        } catch (Exception e) {
//            log.error("Error during creating context", e);
//        }
        log.info("Starting server");
        new HttpServerContext(config, handlers).run();
    }

}
