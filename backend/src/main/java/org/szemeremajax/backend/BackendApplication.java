package org.szemeremajax.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Holds the entrypoint of teh application.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackendApplication {
    /**
     * The entrypoint of the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
