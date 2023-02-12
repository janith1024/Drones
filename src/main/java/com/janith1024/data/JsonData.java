package com.janith1024.data;

import java.util.ArrayList;
import java.util.List;

public class JsonData {
    private final List<Drone> drones;

    public JsonData() {
        this.drones = new ArrayList<>();
    }

    public List<Drone> getDrones() {
        return this.drones;
    }

    public void addDrone(Drone drone) {
        this.drones.add(drone);
    }

}
