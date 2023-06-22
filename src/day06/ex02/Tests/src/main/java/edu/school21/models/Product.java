package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private Long price;

    public Product(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            Product tmp = (Product)obj;
            return id.equals(tmp.id) && name.equals(tmp.name)
                    && price.equals(tmp.price);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return String.format("id=%d,name=\"%s\",price=%d", id, name, price);
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
