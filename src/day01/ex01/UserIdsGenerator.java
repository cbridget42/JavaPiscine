package day01.ex01;

public class UserIdsGenerator {
    private static UserIdsGenerator instance = null;
    private Integer id = 0;

    private UserIdsGenerator() {}

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public Integer getId() {return id;}

    public int generateId() {return ++id;}

    @Override
    public String toString() {
        return String.format("current id %s%n", id);
    }
}
