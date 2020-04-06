package oop.complectors;

import oop.complectors.wagons.PassengerWagon;
import oop.complectors.wagons.RestaurantWagon;

import java.util.ArrayList;

public class PassengerTrain extends Train {
    ArrayList<PassengerWagon> wagons = new ArrayList<>();
    RestaurantWagon restaurantWagon;
    public void setRestaurantWagon(RestaurantWagon restaurantWagon) {
        this.restaurantWagon = restaurantWagon;
    }
    public ArrayList<PassengerWagon> getWagons() {
        return wagons;
    }
    public double getTotalWeight() {
        double result = 0.0;
        for (Wagon wagon : wagons) {
            result += wagon.getTotalWeight();
        }
        if (restaurantWagon != null) {
            result += restaurantWagon.getTotalWeight();
        }
        for (Locomotive locomotive : locomotives) {
            result += locomotive.getTotalWeight();
        }
        return result;
    }

    @Override
    public int getCountOfWagons() {
        return wagons.size();
    }

    @Override
    public String getType() {
        return "Passenger train";
    }


    public void addWagon(PassengerWagon wagon) {
        wagons.add(wagon);
    }

    public PassengerTrain() {

    }
}
