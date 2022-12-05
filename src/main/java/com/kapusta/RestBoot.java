package com.kapusta;

import com.kapusta.config.RestBootConfig;
import com.kapusta.context.ContextProducer;
import com.kapusta.http.HttpServerContext;
import com.kapusta.scanner.ComponentScanner;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Slf4j
public class RestBoot {

    private final RestBootConfig config;

    public void run(){
        log.info("Received signal to start application");
        log.info("Scanning components");
        Set<Class> components = new ComponentScanner(config).scan();
        Map<String, HttpHandler> handlers = null;
        try {
            handlers = new ContextProducer().produceContext(components);
        } catch (Exception e) {
            log.error("Error during creating context", e);
        }
        log.info("Starting server");
        new HttpServerContext(config, handlers).run();
    }

}
