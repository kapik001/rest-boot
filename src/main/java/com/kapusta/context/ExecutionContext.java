package com.kapusta.context;

import com.kapusta.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ExecutionContext {
    private Class<?> executionClass;
    private HttpMethod httpMethod;
    private Method method;
    private String path;
    private ContextMap cm;
    //TODO arguments

    public Object execute() throws InvocationTargetException, IllegalAccessException {
        Object objectToExecuteOn = new ExecutionObjectProducer(this.cm).produceExecutionObject();
        Object toReturn = method.invoke(objectToExecuteOn);
        if(method.getReturnType().equals(Void.class)){
            return null;
        } else return toReturn;
    }
}
