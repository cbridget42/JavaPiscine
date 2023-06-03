package day01.ex02;

public class Program {
    private static final int TEST_SIZE = 15;

    public static void main(String[] args) {
        UsersList test = new UsersArrayList();
        for (int i = 0; i < TEST_SIZE; i++) {
            test.addUser(new User("test_" + i, TEST_SIZE + i));
        }

        for (int i = 0; i < test.getNumberOfUsers(); i++) {
            System.out.println(test.getUserByIndex(i).toString());
        }

        UsersArrayList tmp = (UsersArrayList)test;
        System.out.println("capacity = " + tmp.getCapacity());

        try {
            test.getUserById(-10);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
