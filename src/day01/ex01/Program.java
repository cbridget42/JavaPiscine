package day01.ex01;

public class Program {
    public static void main(String[] args) {
        System.out.print(UserIdsGenerator.getInstance().toString());
        User test1 = new User("test", 42);
        System.out.print(UserIdsGenerator.getInstance().toString());
        User test2 = new User("test", 42);
        System.out.print(UserIdsGenerator.getInstance().toString());
    }
}
