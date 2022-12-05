package com.kapusta.context;

import com.kapusta.annotation.GetEndpoint;
import com.kapusta.annotation.Restful;
import com.kapusta.http.GetHttpHandler;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ContextProducer {
    public Map<String, HttpHandler> produceContext(Set<Class> components) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String, HttpHandler> map = new HashMap<>();
        Class component = components.iterator().next();
        if(component.isAnnotationPresent(Restful.class)){
            Method method = component.getMethods()[0];
            if(method.isAnnotationPresent(GetEndpoint.class)){
                GetEndpoint getAnnotation = method.getAnnotation(GetEndpoint.class);
                String path = getAnnotation.path();
                map.put(path, new GetHttpHandler(component.getConstructor().newInstance(), method));
                log.info("Registered get endpoint at {} for {}", path, component);
            }
        }
        return map;
    }
}
