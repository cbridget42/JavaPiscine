package day01.ex00;

public class Program {
    public static void main(String[] args) {
        User mike = new User("Mike", 1000);
        System.out.println(mike);

        User john = new User("John", 1000);
        System.out.println(john);

        Transaction trans = new Transaction(mike, john,500);
        System.out.println(trans);

        User elizabeth = new User("Elizabeth", 800);
        System.out.println(elizabeth);

        Transaction trans2 = new Transaction(john, elizabeth, -300);
        System.out.println(trans2 + "\n" + mike + "\n" + john + "\n" + elizabeth);
    }
}
