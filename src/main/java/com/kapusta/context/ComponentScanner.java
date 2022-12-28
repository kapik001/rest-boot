package com.kapusta.context;

import com.kapusta.annotation.Injectable;
import com.kapusta.annotation.Restful;
import com.kapusta.config.RestBootConfig;
import com.kapusta.context.RestBootContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

@Slf4j
class ComponentScanner {

    ScannedComponents scan(String[] packagesToScan){
        return scanPackages(packagesToScan);
    }

    private ScannedComponents scanPackages(String[] packages) {
        ScannedComponents components = new ScannedComponents();
        for(String pack: packages){
            Reflections reflections = new Reflections(pack);
            components.addEndpoints(reflections.getTypesAnnotatedWith(Restful.class));
            components.addServices(reflections.getTypesAnnotatedWith(Injectable.class));
        }
        return components;
    }
}
