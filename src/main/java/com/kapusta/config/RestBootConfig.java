package com.kapusta.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestBootConfig {
    private int port;
    private String hostName;
    private int threadPoolSize;
    private String[] packagesToScan;
}
