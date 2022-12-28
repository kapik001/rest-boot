package com.kapusta.context;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
class ScannedComponents {
    private final Set<Class<?>> endpoints;
    private final Set<Class<?>> services;

    ScannedComponents() {
        this.endpoints = new HashSet<>();
        this.services = new HashSet<>();
    }

    void addEndpoints(Set<Class<?>> endpoints){
        this.endpoints.addAll(endpoints);
    }

    void addServices(Set<Class<?>> services){
        this.services.addAll(services);
    }

    public int numberOfScannedComponents(){
        return this.endpoints.size() + this.services.size();
    }

}
