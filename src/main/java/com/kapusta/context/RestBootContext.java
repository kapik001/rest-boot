package com.kapusta.context;

import com.kapusta.config.RestBootConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class RestBootContext {
    private final RestBootConfig restBootConfig;

    RestBootContext(RestBootConfig restBootConfig) {
        this.restBootConfig = restBootConfig;
    }

    public void createContext() throws ContextCreationException {
        log.info("Starting to scan components");
        ScannedComponents scannedComponents = new ComponentScanner().scan(restBootConfig.getPackagesToScan());
        log.info("Scanning finished, detected {} components", scannedComponents.numberOfScannedComponents());
        Set<ContextMap> contextMap = new ContextMapCreator(scannedComponents.getEndpoints()).createContextMap(scannedComponents.getServices());
        log.info("Context map created");
        Set<ExecutionContext> executionContexts = new ExecutionContextProducer(scannedComponents.getEndpoints()).produceExecutionContext();
        executionContexts.forEach(ec -> log.info("Created execution context: {}", ec));
    }




}
