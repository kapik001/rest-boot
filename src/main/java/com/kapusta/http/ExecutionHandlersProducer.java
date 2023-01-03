package com.kapusta.http;

import com.kapusta.context.ExecutionContext;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ExecutionHandlersProducer {
    private Set<ExecutionContext> executionContexts;

    public Map<String, ExecutionHttpHandler> produce(){
        return this.executionContexts.stream().collect(Collectors.toMap(ExecutionContext::getPath, ExecutionHttpHandler::new));
    }
}
