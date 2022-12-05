package com.kapusta.scanner;

import com.kapusta.annotation.Restful;
import com.kapusta.config.RestBootConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class ComponentScanner {

    private final RestBootConfig config;

    public Set<Class> scan(){
        String[] packagesToScan = config.getPackagesToScan();
        return scanPackages(packagesToScan);
    }

    private Set<Class> scanPackages(String[] packages) {
        Set<Class> components = new HashSet<>();
        for(String pack: packages){
            Reflections reflections = new Reflections(pack);
            components.addAll(reflections.getTypesAnnotatedWith(Restful.class));
        }
        log.info("Detected {} components", components.size());
        return components;
    }
}
