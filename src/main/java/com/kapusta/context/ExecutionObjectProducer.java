package com.kapusta.context;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
class ExecutionObjectProducer {

    private final ContextMap contextMap;

    ExecutionObjectProducer(ContextMap contextMap) {
        this.contextMap = contextMap;
    }

    @Nullable
    Object produceExecutionObject() {
        try {
            return this.produceExecutionObject(this.contextMap, new HashSet<>());
        } catch (Exception e) {
            log.info("Could not create execution object", e);
            return null;
        }

    }

    private Object produceExecutionObject(ContextMap cm, Set<Object> objectPool) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> objectToCreateClass = cm.getClazz();
        Constructor<?> constructor = objectToCreateClass.getConstructors()[0];
        Map<Class<?>, Object> objectMap = new HashMap<>();
        for (ContextMap o : cm.getDependencies()) {
            Optional<Object> objectFromPool = objectPool.stream().filter(ob -> ob.getClass().equals(o.getClazz())).findFirst();
            if (objectFromPool.isPresent()) {
                Object object = objectFromPool.get();
                objectMap.put(object.getClass(), object);
            } else {
                Object producedExecutionObject = this.produceExecutionObject(o, objectPool);
                objectMap.put(producedExecutionObject.getClass(), producedExecutionObject);
            }
        }
        Object object = constructor.newInstance(Stream.of(constructor.getParameterTypes()).map(objectMap::get).toArray());
        objectPool.add(object);
        return object;
    }
}
