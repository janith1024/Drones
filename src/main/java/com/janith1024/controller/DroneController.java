package com.janith1024.controller;

import com.janith1024.JsonDataHandler;
import com.janith1024.data.DeliveryItem;
import com.janith1024.data.Drone;
import com.janith1024.data.Medication;
import com.janith1024.data.State;
import com.janith1024.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drones")
public class DroneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroneController.class);

    //---------------------- the listed services

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> load(@RequestBody Drone drone) {
        if (isValid(drone)) {
            if (JsonDataHandler.getData().addDrone(drone)) {
                return new ResponseEntity<>("Success", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Already available", HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/load/{serialNumber}", consumes = "application/json")
    public ResponseEntity<String> load(@RequestBody List<Medication> medications, @PathVariable("serialNumber") String serialNumber) {
        Optional<Drone> bySerial = findBySerial(serialNumber);
        if (bySerial.isPresent() && setToLoading(bySerial.get()) && isOkayToLoad(bySerial.get(), medications)
                && medications.stream().allMatch(this::isValid)) {
            bySerial.get().addDeliveryItem(new DeliveryItem().setMedications(medications));
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/checkLoad/{serialNumber}", produces = "application/json")
    public ResponseEntity<List<Medication>> checkLoad(@PathVariable("serialNumber") String serialNumber) {
        return findBySerial(serialNumber).map(drone -> new ResponseEntity<>(drone.getDeliveryItems().stream()
                        .map(DeliveryItem::getMedications).flatMap(Collection::stream).collect(Collectors.toList()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "/findAvailable", produces = "application/json")
    public ResponseEntity<List<Drone>> getAvailable() {
        return new ResponseEntity<>(JsonDataHandler.getData().getDrones().stream().filter(drone -> drone.getState() == State.IDLE)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/getBatteryLevel")
    public ResponseEntity<Double> getBatteryLevel(@RequestParam("serialNumber") String serialNumber) {
        return findBySerial(serialNumber).map(Drone::getBatteryCapacity).map(c -> new ResponseEntity<>(c, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    //---------------------------other services

    /**
     * test method to check the service
     **/
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        LOGGER.info("test ok");
        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Set<Drone>> getDrones() {
        return new ResponseEntity<>(JsonDataHandler.getData().getDrones(), HttpStatus.OK);
    }

    @GetMapping(value = "/findBySerial", produces = "application/json")
    public ResponseEntity<Drone> getDroneBySerialNumber(@RequestParam("serialNumber") String serialNumber) {
        return findBySerial(serialNumber).map(drone -> new ResponseEntity<>(drone, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "/findByStatus", produces = "application/json")
    public ResponseEntity<List<Drone>> getDroneByStatus(@RequestParam("status") String state) {
        try {
            return new ResponseEntity<>(JsonDataHandler.getData().getDrones().stream().filter(drone -> drone.getState() == State.valueOf(state)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error of the status", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * The drone should be in LOADING status and delivery items should be empty
     *
     * @param drone       Drone
     * @param medications DeliveryItem
     * @return true only if the drone is able to load this delivery item
     */
    private boolean isOkayToLoad(Drone drone, List<Medication> medications) {
        return drone != null && drone.getState() == State.LOADING && drone.getDeliveryItems().isEmpty()
                && medications != null && medications.stream().mapToDouble(Medication::getWeight).sum() <= drone.getModel().getWeightLimit();
    }


    /**
     * This method check the drone is okay to change to LOADING by checking the conditions and change
     *
     * @param drone Drone
     * @return true if and only if the status change to LOADING
     */
    private boolean setToLoading(Drone drone) {
        if (drone != null && (drone.getState() == State.IDLE || drone.getState() == State.LOADING) && drone.getBatteryCapacity() > 25) {
            drone.setState(State.LOADING);
            return true;
        }
        return false;
    }

    private Optional<Drone> findBySerial(String serialNumber) {
        return JsonDataHandler.getData().getDrones().stream().filter(drone -> drone.getSerial().equalsIgnoreCase(serialNumber)).findAny();
    }

    private boolean isValid(Drone drone) {
        return drone != null && (drone.getSerial() != null && drone.getSerial().length() <= 100) && drone.getModel() != null && drone.getState() != null;
    }

    private boolean isValid(Medication medication) {
        return StringUtil.allowedOnlyLettersNumbersDashUnderscore(medication.getName())
                && StringUtil.allowedOnlyUpperLettersNumbersUnderscore(medication.getCode());
    }


}
