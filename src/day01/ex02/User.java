package day01.ex02;

import day01.ex01.UserIdsGenerator;

public class User {
    private final Integer identifier;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        setName(name);
        setBalance(balance);
    }

    @Override
    public String toString() {
        return String.format("User%nname: %s%nid: %d%nbalance: %d%n",
                name, identifier, balance);
    }

    public void setBalance(Integer amount) {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            this.balance = 0;
        }
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getIdentifier() {
        return this.identifier;
    }
}
