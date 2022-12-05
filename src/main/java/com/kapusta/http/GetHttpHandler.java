package com.kapusta.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class GetHttpHandler implements HttpHandler {

    private final Object objectToInvoke;
    private final Method methodToInvoke;

    public void handle(HttpExchange exchange) throws IOException {
        Object result;
        try {
            result = methodToInvoke.invoke(objectToInvoke);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        byte[] response =  result.toString().getBytes();
        exchange.sendResponseHeaders(200, response.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }
}
