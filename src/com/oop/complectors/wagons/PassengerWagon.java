package oop.complectors.wagons;

import oop.Constants;
import oop.complectors.Wagon;
import oop.queries.PassengerQuery;

public abstract class PassengerWagon extends Wagon {
    protected int countOfPeople;

    public void setCountOfPeople(int countOfPeople) {
        assert(countOfPeople <= getMaxCountOfPeople());
        this.countOfPeople = countOfPeople;
    }
    public abstract int getMaxCountOfPeople(); // вместимость вагона, нужно для подсчета необходимого количества
    public final double getTotalWeight() {
        return getMass() + countOfPeople * 0.1;
    }
}
