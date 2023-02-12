package com.janith1024.controller;

import com.janith1024.JsonDataHandler;
import com.janith1024.data.Drone;
import com.janith1024.data.Medication;
import com.janith1024.data.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drones")
public class DroneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroneController.class);

    /**
     * test method to check the service
     **/
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        LOGGER.info("test ok");
        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Drone>> getDrones() {
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

    @PostMapping(value = "/load/{serialNumber}", consumes = "application/json")
    public ResponseEntity<String> load(@RequestBody List<Medication> medications, @PathVariable("serialNumber") String serialNumber) {
        Optional<Drone> bySerial = findBySerial(serialNumber);
        if (bySerial.isPresent()) {
            bySerial.get().setLoad(medications);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private Optional<Drone> findBySerial(String serialNumber) {
        return JsonDataHandler.getData().getDrones().stream().filter(drone -> drone.getSerial().equalsIgnoreCase(serialNumber)).findAny();
    }

}
