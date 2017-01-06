package com.osrapi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** extends SpringBootServletInitializer for creating a deployable war file. */
@ComponentScan(basePackages = "com.osrapi.controllers")
@EntityScan(basePackages = "com.osrapi.models")
@EnableJpaRepositories(basePackages = "com.osrapi.repositories")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    /**
     * Application entry point.
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected final SpringApplicationBuilder configure(
            final SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
