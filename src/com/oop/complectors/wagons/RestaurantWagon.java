package oop.complectors.wagons;

import oop.Constants;
import oop.complectors.Wagon;

public class RestaurantWagon extends Wagon {
    public double getMass() {
        return Constants.RESTAURANT_MASS;
    }
    public double getTotalWeight() {
        return getMass();
    }
}
