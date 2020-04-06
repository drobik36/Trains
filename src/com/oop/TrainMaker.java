package oop;

import oop.cargo.Cargo;
import oop.cargo.FreightCargo;
import oop.cargo.LiquidCargo;
import oop.cargo.MachineCargo;
import oop.complectors.*;
import oop.complectors.wagons.FreightWagon;
import oop.complectors.wagons.PassengerWagon;
import oop.complectors.wagons.RestaurantWagon;
import oop.complectors.wagons.freight.CoveredFreightWagon;
import oop.complectors.wagons.freight.PlatformFreightWagon;
import oop.complectors.wagons.freight.TankFreightWagon;
import oop.complectors.wagons.passenger.CoupePassengerWagon;
import oop.complectors.wagons.passenger.SVPassengerWagon;
import oop.complectors.wagons.passenger.RSPassengerWagon;
import oop.queries.FreightQuery;
import oop.queries.PassengerQuery;
import oop.queries.Query;

import java.util.ArrayList;

public class TrainMaker {
    private <T extends PassengerWagon>
    void addPassengerWagons(Class<T> classOfWagon, int countOfPeople, ArrayList<PassengerWagon> wagons) {
        try {
            while (countOfPeople > 0) {
                PassengerWagon wagon = classOfWagon.getDeclaredConstructor().newInstance();
                int take = countOfPeople >= wagon.getMaxCountOfPeople() ? wagon.getMaxCountOfPeople() : countOfPeople;
                wagon.setCountOfPeople(take);
                wagons.add(wagon);
                countOfPeople -= take;
            }
        } catch (Exception ignored) { }
    }

    public ArrayList<Train> perform(Query query) {
        ArrayList<Train> result = new ArrayList<>();
        if (query instanceof PassengerQuery) {
            PassengerQuery pQuery = (PassengerQuery) query;
            ArrayList<PassengerWagon> wagons = new ArrayList<>();
            this.addPassengerWagons(SVPassengerWagon.class, pQuery.getCountSV(), wagons);
            this.addPassengerWagons(RSPassengerWagon.class, pQuery.getCountRS(), wagons);
            this.addPassengerWagons(CoupePassengerWagon.class, pQuery.getCountCoupe(), wagons);
            PassengerTrain curr = null;
            for (PassengerWagon wagon : wagons) {
                if (curr == null) {
                    curr = new PassengerTrain();
                    curr.addLocomotive(new Locomotive());
                    curr.setRestaurantWagon(new RestaurantWagon());
                }
                if (curr.getTotalWeight() + wagon.getTotalWeight() <= curr.getTotalTrainPower()) {
                    curr.addWagon(wagon);
                } else {
                    if (curr.getLocomotiveCount() < 3) {
                        curr.addLocomotive(new Locomotive());
                    } else {
                        result.add(curr);
                        curr = null;
                    }
                }
            }
            if (curr != null) {
                result.add(curr);
                curr = null;
            }
        } else if (query instanceof FreightQuery) {
            FreightQuery fQuery = (FreightQuery) query;
            ArrayList<FreightWagon> wagons = new ArrayList<>();

            for (Cargo cargo : fQuery.getCargoes()) {
                if (cargo instanceof FreightCargo) {
                    FreightCargo fCargo = (FreightCargo) cargo;
                    double mass = fCargo.getMass();
                    while (mass > 0) {
                        CoveredFreightWagon wagon = new CoveredFreightWagon();
                        double take = Math.min(mass, wagon.getMaxCargoMass());
                        wagon.setMassOfCargo(take);
                        wagons.add(wagon);
                        mass -= take;
                    }
                } else if (cargo instanceof LiquidCargo) {
                    LiquidCargo liquidCargo = (LiquidCargo) cargo;
                    double volume = liquidCargo.getVolume();
                    while (volume > 0) {
                        TankFreightWagon wagon = new TankFreightWagon();
                        double take = Math.min(volume, wagon.getMaxCargoVolume());
                        wagon.setCargo(liquidCargo, take);
                        wagons.add(wagon);
                        volume -= take;
                    }
                } else if (cargo instanceof MachineCargo) {
                    MachineCargo mCargo = (MachineCargo) cargo;
                    int count = mCargo.getCount();
                    while (count > 0) {
                        PlatformFreightWagon wagon = new PlatformFreightWagon();
                        int take = Math.min(count, Math.min(
                            (int) (wagon.getMaxCargoMass() / mCargo.getMassOfOne()),
                            mCargo.getCarLimitPerPlatform()
                        ));
                        wagon.setCargo(mCargo, take);
                        wagons.add(wagon);
                        count -= take;
                    }
                }
            }
            FreightTrain curr = null;
            for (FreightWagon wagon : wagons) {
                if (curr == null) {
                    curr = new FreightTrain();
                    curr.addLocomotive(new Locomotive());
                }
                if (curr.getTotalWeight() + wagon.getTotalWeight() <= curr.getTotalTrainPower()) {
                    curr.addWagon(wagon);
                } else {
                    if (curr.getLocomotiveCount() < 3) {
                        curr.addLocomotive(new Locomotive());
                    } else {
                        result.add(curr);
                        curr = null;
                    }
                }
            }
            if (curr != null) {
                result.add(curr);
                curr = null;
            }
        }

        for (Train train : result) {
            assert(train.getLocomotiveCount() >= 1 && train.getLocomotiveCount() <= 3);
            assert(train.getTotalTrainPower() >= train.getTotalWeight());
        }
        return result;
    }


}
