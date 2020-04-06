package oop.cargo;

public class LiquidCargo extends Cargo {
    private double volume;
    private double density;

    public LiquidCargo(int volume, double density) {
        this.volume = volume;
        this.density = density;
    }

    public double getVolume() { return volume; }

    public double getDensity() { return density; }

    public String getType() { return "liquid"; }

}
