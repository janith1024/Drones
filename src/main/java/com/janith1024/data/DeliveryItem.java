package com.janith1024.data;

import java.util.List;
import java.util.Objects;

public class DeliveryItem {
    private List<Medication> medications;
    private String deliveryLocation;

    public List<Medication> getMedications() {
        return medications;
    }

    public DeliveryItem setMedications(List<Medication> medications) {
        this.medications = medications;
        return this;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public DeliveryItem setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
        return this;
    }

    public double totalWeight(){
        if(medications != null){
           return medications.stream().mapToDouble(Medication::getWeight).sum();
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryItem that = (DeliveryItem) o;
        return Objects.equals(getMedications(), that.getMedications()) && Objects.equals(getDeliveryLocation(), that.getDeliveryLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMedications(), getDeliveryLocation());
    }
}
