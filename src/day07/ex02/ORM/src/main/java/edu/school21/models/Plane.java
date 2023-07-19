package edu.school21.models;

import edu.school21.orm.OrmColumn;
import edu.school21.orm.OrmColumnId;
import edu.school21.orm.OrmEntity;

@OrmEntity(table = "airbus")
public class Plane {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "model", length = 10)
    private String model;
    @OrmColumn(name = "country", length = 20)
    private String country;
    @OrmColumn(name = "speed")
    private Double speed;

    public Plane(Long id, String model, String country, Double speed) {
        this.id = id;
        this.model = model;
        this.country = country;
        this.speed = speed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", country='" + country + '\'' +
                ", speed=" + speed +
                '}';
    }
}
