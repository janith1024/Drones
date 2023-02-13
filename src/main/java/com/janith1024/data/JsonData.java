package com.janith1024.data;

import java.util.HashSet;
import java.util.Set;

public class JsonData {
    private final Set<Drone> drones;
    private long version;

    public JsonData() {
        this.drones = new HashSet<>();
    }

    public Set<Drone> getDrones() {
        return this.drones;
    }

    public boolean addDrone(Drone drone) {
        return this.drones.add(drone);
    }

    public JsonData incrementVersion() {
        version++;
        return this;
    }

}
