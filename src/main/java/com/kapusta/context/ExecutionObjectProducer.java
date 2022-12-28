package com.kapusta.context;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
            return null;
        }

    }

    private Object produceExecutionObject(ContextMap cm, Set<Object> objectPool) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> objectToCreateClass = cm.getClazz();
        Constructor<?> constructor = objectToCreateClass.getConstructors()[0];
        Object object;
        List<Object> list = new ArrayList<>();
        for (ContextMap o : cm.getDependencies()) {
            Optional<Object> objectFromPool = objectPool.stream().filter(ob -> ob.getClass().equals(o.getClazz())).findFirst();
            if (objectFromPool.isPresent()) {
                list.add(objectFromPool.get());
            } else {
                Object produceExecutionObject = this.produceExecutionObject(o, objectPool);
                list.add(produceExecutionObject);
            }
        }
        object = constructor.newInstance(list.toArray());
        objectPool.add(object);
        return object;
    }
}
