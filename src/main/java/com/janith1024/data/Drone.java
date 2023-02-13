package com.janith1024.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drone {
    private String serial;
    private Model model;
    private double batteryCapacity;
    private State state;
    //For the moment the requirement is to deliver one item at a time. But I use list to keep the extendability in future
    private final List<DeliveryItem> deliveryItems = new ArrayList<>();

    public String getSerial() {
        return serial;
    }

    public Drone setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public Model getModel() {
        return model;
    }

    public Drone setModel(Model model) {
        this.model = model;
        return this;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public Drone setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public State getState() {
        return state;
    }

    public Drone setState(State state) {
        this.state = state;
        return this;
    }

    public List<DeliveryItem> getDeliveryItems() {
        return deliveryItems;
    }

    public void addDeliveryItem(DeliveryItem deliveryItem) {
        this.deliveryItems.add(deliveryItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(getSerial(), drone.getSerial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSerial());
    }
}
