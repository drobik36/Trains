package oop.cargo;

public class MachineCargo extends Cargo {
    private int count;
    private double massOfOne;
    private int carLimitPerPlatform;

    public MachineCargo(int count, double massOfOne, int carLimitPerPlatform) {
        this.count = count;
        this.massOfOne = massOfOne;
        this.carLimitPerPlatform = carLimitPerPlatform;
    }

    public int getCount() {
        return count;
    }

    public double getMassOfOne() {
        return massOfOne;
    }

    public int getCarLimitPerPlatform() {
        return carLimitPerPlatform;
    }

    public String getType() { return "machine"; }

}
