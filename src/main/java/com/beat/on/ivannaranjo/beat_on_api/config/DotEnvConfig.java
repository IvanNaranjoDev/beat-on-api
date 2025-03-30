package com.beat.on.ivannaranjo.beat_on_api.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvConfig {
    static {
        try {
            Dotenv dotenv = Dotenv.configure().load();

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
