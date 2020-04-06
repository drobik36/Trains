package oop.complectors;


import java.util.ArrayList;

public abstract class Train {
    ArrayList<Locomotive> locomotives = new ArrayList<>();
    private double totalTrainPower;

    public double getTotalTrainPower() {
        return totalTrainPower;
    }
    public int getLocomotiveCount() {
        return locomotives.size();
    }
    public abstract double getTotalWeight();
    public abstract int getCountOfWagons();
    public abstract String getType();
    public void addLocomotive(Locomotive curr) {
        locomotives.add(curr);
        totalTrainPower += curr.getPower();
        assert(locomotives.size() <= 3);
    }
}
