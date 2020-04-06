package oop.queries;

public class PassengerQuery extends Query {
    private int countSV;
    private int countCoupe;
    private int countRS;

    public PassengerQuery(int countSV, int countCoupe, int countRS) {
        this.countSV = countSV;
        this.countCoupe = countCoupe;
        this.countRS = countRS;
    }

    public int getCountSV() {
        return countSV;
    }

    public int getCountCoupe() {
        return countCoupe;
    }

    public int getCountRS() {
        return countRS;
    }

    public String getType(){
        return "Passenger";
    }
}
