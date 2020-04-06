package oop.complectors.wagons.freight;

import oop.Constants;
import oop.cargo.MachineCargo;
import oop.complectors.wagons.FreightWagon;

public class PlatformFreightWagon extends FreightWagon {
    protected double massOfCargo;
    public double getMass() {
        return Constants.PLATFORM_MASS;
    }
    public double getTotalWeight() {
        return getMass() + massOfCargo;
    }
    public double getMaxCargoMass() {
        return Constants.PLATFORM_MAX_IN;
    }
    public void setCargo(MachineCargo cargo, int countOfMachines) {
        assert(cargo.getCarLimitPerPlatform() >= countOfMachines);
        this.massOfCargo = cargo.getMassOfOne() * countOfMachines;
    }
}
