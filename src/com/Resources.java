import oop.complectors.FreightTrain;
import oop.complectors.PassengerTrain;
import oop.complectors.Train;
import oop.complectors.wagons.FreightWagon;
import oop.complectors.wagons.PassengerWagon;
import oop.complectors.wagons.freight.CoveredFreightWagon;
import oop.complectors.wagons.freight.PlatformFreightWagon;
import oop.complectors.wagons.freight.TankFreightWagon;
import oop.complectors.wagons.passenger.CoupePassengerWagon;
import oop.complectors.wagons.passenger.RSPassengerWagon;
import oop.complectors.wagons.passenger.SVPassengerWagon;

import java.util.ArrayList;

public class Resources {
    private int countLocomotives;
    private int countRestaurantWagons;
    private int countCoupe;
    private int countRS;
    private int countSV;
    private int countCovered;
    private int countPlatform;
    private int countTanks;

    private static Resources calculateResources(Train train) throws Exception {
        Resources result = new Resources();
        result.countLocomotives += train.getLocomotiveCount();
        if (train instanceof FreightTrain) {
            ArrayList<FreightWagon> wagons = ((FreightTrain) train).getWagons();
            for (FreightWagon wagon : wagons) {
                if (wagon instanceof CoveredFreightWagon) result.countCovered++;
                if (wagon instanceof PlatformFreightWagon) result.countPlatform++;
                if (wagon instanceof TankFreightWagon) result.countTanks++;
            }
        } else if (train instanceof PassengerTrain) {
            ArrayList<PassengerWagon> wagons = ((PassengerTrain) train).getWagons();
            for (PassengerWagon wagon : wagons) {
                if (wagon instanceof CoupePassengerWagon) result.countCoupe++;
                if (wagon instanceof RSPassengerWagon) result.countRS++;
                if (wagon instanceof SVPassengerWagon) result.countSV++;
            }
            result.countRestaurantWagons++;
        } else {
            throw new Exception("unknown train type");
        }
        return result;
    }

    public void addResources(Train train) throws Exception {
        Resources adding = calculateResources(train);
        this.countRestaurantWagons += adding.countRestaurantWagons;
        this.countSV += adding.countSV;
        this.countRS += adding.countRS;
        this.countCoupe += adding.countCoupe;
        this.countTanks += adding.countTanks;
        this.countPlatform += adding.countPlatform;
        this.countCovered += adding.countCovered;
        this.countLocomotives += adding.countLocomotives;
    }

    public boolean enoughResources(Resources compare) throws Exception {
        return this.countRestaurantWagons >= compare.countRestaurantWagons &&
                this.countSV >= compare.countSV &&
                this.countRS >= compare.countRS &&
                this.countCoupe >= compare.countCoupe &&
                this.countCovered >= compare.countCovered &&
                this.countPlatform >= compare.countPlatform &&
                this.countTanks >= compare.countTanks &&
                this.countLocomotives >= compare.countLocomotives;

    }

    public boolean takeResources(Train train) throws Exception {
        Resources taken = calculateResources(train);
        if (!this.enoughResources(taken)) {
            return false;
        }
        this.countRestaurantWagons -= taken.countRestaurantWagons;
        this.countSV -= taken.countSV;
        this.countRS = this.countRS - taken.countRS;
        this.countCoupe = this.countCoupe - taken.countCoupe;
        this.countTanks = this.countTanks - taken.countTanks;
        this.countPlatform = this.countPlatform - taken.countPlatform;
        this.countCovered = this.countCovered - taken.countCovered;
        this.countLocomotives = this.countLocomotives - taken.countLocomotives;
        return true;
    }
}
