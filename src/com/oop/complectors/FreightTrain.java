package oop.complectors;

import oop.complectors.wagons.FreightWagon;

import java.util.ArrayList;

public class FreightTrain extends Train {
    ArrayList<FreightWagon> wagons = new ArrayList<>();
    public ArrayList<FreightWagon> getWagons() {
        return wagons;
    }
    public double getTotalWeight() {
        double result = 0.0;
        for (Wagon wagon : wagons) {
            result += wagon.getTotalWeight();
        }
        for (Locomotive locomotive : locomotives) {
            result += locomotive.getTotalWeight();
        }
        return result;
    }
    public int getCountOfWagons() {
        return wagons.size();
    }
    public String getType() {
        return "Freight train";
    }

    public void addWagon(FreightWagon wagon) {
        wagons.add(wagon);
    }

    public FreightTrain() {
    }
}
