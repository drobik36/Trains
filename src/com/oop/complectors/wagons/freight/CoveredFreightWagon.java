package oop.complectors.wagons.freight;

import oop.Constants;
import oop.complectors.wagons.FreightWagon;

public class CoveredFreightWagon extends FreightWagon {
    protected double massOfCargo;
    public double getMaxCargoMass() { return Constants.COVERED_MAX_IN; }
    public double getMass() { return Constants.COVERED_MASS; }
    public double getTotalWeight() {
        return getMass() + massOfCargo;
    }
    public void setMassOfCargo(double massOfCargo) {
        this.massOfCargo = massOfCargo;
    }
}
