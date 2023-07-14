package edu.school21.classes;

public class Car {
    private String model;
    private String country;
    private int speed;

    public Car() {
        this.model = "Default model";
        this.country = "Default country";
        this.speed = 0;
    }

    public Car(String model, String country, int speed) {
        this.model = model;
        this.country = country;
        this.speed = speed;
    }

    public int speedUp(int val) {
        this.speed += val;
        return speed;
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
