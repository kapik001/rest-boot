package com.kapusta.context;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ContextMap {
    @Getter
    @Setter
    private Class<?> clazz;
    private Set<ContextMap> dependencies;

    ContextMap(){
        this.dependencies = new HashSet<>();
    }

    void addDependencies(Set<ContextMap> contextMap){
        dependencies.addAll(contextMap);
    }

    Set<ContextMap> getDependencies(){
        return Collections.unmodifiableSet(dependencies);
    }

}
