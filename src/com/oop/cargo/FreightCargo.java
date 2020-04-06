package oop.cargo;

public class FreightCargo extends Cargo {
    private double mass;
    public FreightCargo(int mass){
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public String getType() { return "freight"; }
}
