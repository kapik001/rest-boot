package com.kapusta.context;

import com.kapusta.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
@Builder
@ToString
class ExecutionContext {
    private Class<?> executionClass;
    private HttpMethod httpMethod;
    private Method method;
    private String path;
    //TODO arguments
}
