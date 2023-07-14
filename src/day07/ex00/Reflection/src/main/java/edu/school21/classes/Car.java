package edu.school21.classes;

public class Car {
    private final String model;
    private final String country;
    private long speed;

    public Car() {
        this.model = "Default model";
        this.country = "Default country";
        this.speed = 0;
    }

    public Car(String model, String country, long speed) {
        this.model = model;
        this.country = country;
        this.speed = speed;
    }

    public long speedUp(long val, String str) {
        System.out.println(str);
        this.speed += val;
        return speed;
    }

    public void test() {
        System.out.println("nothing to do");
    }

    @Override
    public String toString() {
        return "Car[" +
                "model='" + model + '\'' +
                ", country='" + country + '\'' +
                ", speed=" + speed +
                ']';
    }
}
