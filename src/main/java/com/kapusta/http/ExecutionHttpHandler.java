package com.kapusta.http;

import com.kapusta.context.ExecutionContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
@Slf4j
public class ExecutionHttpHandler implements HttpHandler {
    private ExecutionContext executionContext;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Object result;
        try {
            result = executionContext.execute();
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
