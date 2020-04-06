package oop.complectors.wagons.passenger;

import oop.Constants;
import oop.complectors.wagons.PassengerWagon;

public class RSPassengerWagon extends PassengerWagon {
    public double getMass() { return Constants.RS_MASS; }
    public int getMaxCountOfPeople() { return Constants.RS_MAX_IN; }
}
