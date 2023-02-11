package com.janith1024;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static boolean error;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String str = context.getEnvironment().getProperty("dataFilePath");
        LOGGER.info("dataFilePath " + str);
        try {
            JsonDataHandler.setFilePath(str);
            JsonDataHandler.loadFromFile();
        } catch (IOException e) {
            LOGGER.error("Fail to read data file", e);
            error = true;
            context.close();
        }
    }

    @PreDestroy
    public void onExit() {
        if(!error)
        JsonDataHandler.saveToFile();
        LOGGER.info("Stop application");
    }
}
