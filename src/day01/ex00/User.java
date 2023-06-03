package day01.ex00;

public class User {
    private static Integer idCounter = 0;
    private final Integer identifier;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.identifier = idCounter++;
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
