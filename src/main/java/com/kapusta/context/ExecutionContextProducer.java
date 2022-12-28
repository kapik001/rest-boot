package com.kapusta.context;

import com.kapusta.annotation.endpoints.GetEndpoint;
import com.kapusta.http.HttpMethod;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
class ExecutionContextProducer {
    private Set<Class<?>> endpoints;

    Set<ExecutionContext> produceExecutionContext(){
        Set<ExecutionContext> contexts = new HashSet<>();
        for(Class<?> clazz: endpoints){
            for(Method method: clazz.getMethods()){
                if (method.isAnnotationPresent(GetEndpoint.class)){
                    contexts.add(createGetContext(clazz, method));
                }
            }
        }
        return contexts;
    }

    private ExecutionContext createGetContext(Class<?> clazz, Method method){
        return ExecutionContext
                .builder()
                .executionClass(clazz)
                .httpMethod(HttpMethod.GET)
                .method(method)
                .path(method.getAnnotation(GetEndpoint.class).path())
                .build();
    }
}
