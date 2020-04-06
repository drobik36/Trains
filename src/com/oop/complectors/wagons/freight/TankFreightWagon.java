package oop.complectors.wagons.freight;

import oop.Constants;
import oop.cargo.LiquidCargo;
import oop.complectors.wagons.FreightWagon;

// Цистерна, а не танк!!!
public class TankFreightWagon extends FreightWagon {
    private double volumeOfCargo; // объём вместимой жидкости в метрах кубических
    private double density;

    public double getMass() {
        return Constants.TANK_MASS;
    }
    public double getMaxCargoVolume() {
        return Constants.TANK_MAX_IN;
    }
    public double getTotalWeight() {
        return getMass() + volumeOfCargo * density;
    }
    public void setCargo(LiquidCargo liquidCargo, double volumeOfCargo) {
        this.volumeOfCargo = volumeOfCargo;
        this.density = liquidCargo.getDensity();
    }
}
