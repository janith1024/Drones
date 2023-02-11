package com.janith1024;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.janith1024.data.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataHandler.class);

    private static final List<JsonData> JSON_DATA = new ArrayList<>();
    private static String filePath;

    /**
     * This method used to save the data to the file
     */
    public static void saveToFile() {
        LOGGER.info("saveToFile");
        saveJson(new Gson().toJson(JSON_DATA));
    }

    /**
     * This method used to load saved data to the memory
     */
    public static void loadFromFile() {
        LOGGER.info("loadFromFile");
        JSON_DATA.clear();
        JSON_DATA.addAll(readFromFile());
    }

    public static void setFilePath(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            LOGGER.info("File is not exist so create one");
            saveToFile();
        }

        JsonDataHandler.filePath = filePath;
    }

    private static List<JsonData> readFromFile() {
        try {
            return new Gson().fromJson(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), new TypeToken<List<JsonData>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found {}", filePath);
        }
        return Collections.emptyList();
    }

    private static void saveJson(String json) {
        LOGGER.info(json);
        if (filePath != null) {
            //write to file
            try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
                fileWriter.write(json);
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }
}
