package oop.complectors;

import oop.Constants;

public class Locomotive extends IronHorse {
    public double getPower() {
        return Constants.LOCOMOTIVE_POWER;
    }

    public double getMass() {
        return Constants.LOCOMOTIVE_MASS;
    }
}
