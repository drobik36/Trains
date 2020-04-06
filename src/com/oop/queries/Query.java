package oop.queries;

import oop.cargo.Cargo;
import oop.cargo.FreightCargo;
import oop.cargo.LiquidCargo;
import oop.cargo.MachineCargo;

import java.util.Random;

public abstract class Query {
    public static Query createRandomQuery() {
        Random r = new Random();
        if (r.nextBoolean()) { // freight
            FreightQuery query = new FreightQuery();
            int n = r.nextInt(6) + 2;
            Cargo cargo;
            for (int i = 0; i < n; i++) {
                switch (r.nextInt(3)) {
                    case 0: // liquid
                        cargo = new LiquidCargo(r.nextInt(1000), r.nextDouble() * 2);
                        break;
                    case 1:
                        cargo = new MachineCargo(r.nextInt(100), r.nextInt(40), r.nextInt(3) + 1);
                        break;
                    case 2:
                        cargo = new FreightCargo(r.nextInt(1000));
                        break;
                    default:
                        throw new RuntimeException("Not supported");
                }
                query.addCargo(cargo);
            }
            return query;
        } else {
            PassengerQuery query = new PassengerQuery(r.nextInt(300), r.nextInt(1000), r.nextInt(2000));
            return query;
        }
    }
    public abstract String getType();
}
