package oop.complectors.wagons.passenger;

import oop.Constants;
import oop.complectors.wagons.PassengerWagon;

public class CoupePassengerWagon extends PassengerWagon {
    public double getMass() { return Constants.COUPE_MASS; }
    public int getMaxCountOfPeople() { return Constants.COUPE_MAX_IN; }
}
