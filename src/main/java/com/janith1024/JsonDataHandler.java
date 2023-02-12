package com.janith1024;

import com.google.gson.Gson;
import com.janith1024.data.Drone;
import com.janith1024.data.JsonData;
import com.janith1024.data.Model;
import com.janith1024.data.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

public class JsonDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataHandler.class);

    private static JsonData data = new JsonData();
    private static String filePath;

    /**
     * This method used to save the data to the file
     */
    public static void saveToFile() {
        LOGGER.info("saveToFile");
        saveJson(new Gson().toJson(data));
    }

    /**
     * This method used to load saved data to the memory
     */
    public static void loadFromFile() {
        LOGGER.info("loadFromFile");
        data = readFromFile();
    }

    public static void setFilePath(String filePath) throws IOException {
        JsonDataHandler.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            LOGGER.info("File is not exist so create one");
            initDrones();
            saveToFile();
        }

    }

    public static JsonData getData() {
        return data;
    }

    private static JsonData readFromFile() {
        try {
            return new Gson().fromJson(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8), JsonData.class);
        } catch (FileNotFoundException e) {
            LOGGER.error("file not found {}", filePath);
        }
        return new JsonData();
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

    /**
     * This is the method with initiate the 10 drones according to the requirement
     */
    private static void initDrones() {
        IntStream.range(0, 10).mapToObj(value -> new Drone().setSerial("1234-578-drt-0" + value)
                .setModel(getModel(value)).setBatteryCapacity(100).setState(State.IDLE)).forEach(data::addDrone);

    }

    private static Model getModel(int value) {
        return Model.values()[value % Model.values().length];
    }
}
