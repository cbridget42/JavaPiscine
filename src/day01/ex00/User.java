package day01.ex00;

public class User {
    private static int idCounter = 0;
    private final int identifier;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.identifier = idCounter++;
        setName(name);
        setBalance(balance);
    }

    @Override
    public String toString() {
        return String.format("User%nname: %s%nid: %d%nbalance: %d%n",
                name, identifier, balance);
    }

    public void setBalance(int amount) {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            this.balance = 0;
        }
    }

    public int getBalance() {
        return this.balance;
    }

    public void setName(String name) {
        this.name = (name.length() > 0) ? name : "default_" + identifier;
    }

    public String getName() {
        return this.name;
    }

    public int getIdentifier() {
        return this.identifier;
    }
}
