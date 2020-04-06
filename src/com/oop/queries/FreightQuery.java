package oop.queries;

import oop.cargo.Cargo;

import java.util.ArrayList;

public class FreightQuery extends Query {
    protected ArrayList<Cargo> cargoes = new ArrayList<>();
    public void addCargo(Cargo cargo) {
        cargoes.add(cargo);
    }
    public ArrayList<Cargo> getCargoes() {
        return cargoes;
    }
    public String getType(){
        return "Freight";
    }
}
