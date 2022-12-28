package com.kapusta.context;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class ContextMapCreator {

    private final Set<Class<?>> classPool;

    ContextMapCreator(Set<Class<?>> classPool) {
        this.classPool = classPool;
    }

    Set<ContextMap> createContextMap(Set<Class<?>> classToScan) throws ContextCreationException {
        return createContextMapIn(classToScan, new HashSet<>());
    }

    private Set<ContextMap> createContextMapIn(Set<Class<?>> classToScan, Set<Class<?>> alreadyVisited) throws ContextCreationException {
        Set<ContextMap> contextMaps = new HashSet<>();
        for (Class<?> clazz : classToScan) {
            ContextMap contextMap = new ContextMap();
            contextMap.setClazz(clazz);
            Constructor<?>[] constructors = clazz.getConstructors();
            if (constructors.length != 1) {
                throw createWrongNumberOfConstructorsException(clazz, constructors.length);
            }
            Constructor<?> constructor = constructors[0];
            Set<Class<?>> parameters = new HashSet<>(Arrays.asList(constructor.getParameterTypes()));
            if (!parameters.isEmpty()) {
                if (!classPool.containsAll(parameters)) {
                    throw createArgumentsNotRecognizedException(parameters, clazz);
                }
                Set<Class<?>> visitedCopy = new HashSet<>(alreadyVisited);
                if (!visitedCopy.add(clazz)) {
                    throw createCyclicDependenciesException(clazz);
                }
                contextMap.addDependencies(createContextMapIn(parameters, visitedCopy));
            }
            contextMaps.add(contextMap);
        }

        return contextMaps;
    }

    private ContextCreationException createCyclicDependenciesException(Class<?> cyclicDependency) {
        return new ContextCreationException(MessageFormat
                .format("Detected cyclic dependencies made using {0} class", cyclicDependency));
    }

    private ContextCreationException createWrongNumberOfConstructorsException(Class<?> clazz, int numberOfConstructors) {
        return new ContextCreationException(MessageFormat
                .format("Constructor of {0} have not correct number of public constructors declared! " +
                        "Should have exactly one, but {1} was detected", clazz, numberOfConstructors));
    }

    private ContextCreationException createArgumentsNotRecognizedException(Set<Class<?>> parameters, Class<?> clazz) {
        parameters.removeAll(classPool);
        return new ContextCreationException(MessageFormat
                .format("Following dependencies {0}, that are part of {1} constructor parameters were not detected as a beans", parameters, clazz));
    }
}

