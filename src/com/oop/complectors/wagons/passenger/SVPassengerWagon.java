package oop.complectors.wagons.passenger;

import oop.Constants;
import oop.complectors.wagons.PassengerWagon;

public class SVPassengerWagon extends PassengerWagon {
    public double getMass() {
        return Constants.SV_MASS;
    }
    public int getMaxCountOfPeople() { return Constants.SV_MAX_IN; }
}
