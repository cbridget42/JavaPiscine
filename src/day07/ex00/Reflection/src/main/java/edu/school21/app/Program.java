package edu.school21.app;

public class Program {
    public static final String SEP = "---------------------";

    public static void main(String[] args) {
        System.out.format("Classes:%n - User%n - Car%n" + SEP + "%nEnter class name:%n");
        try {
            DemoReflection demoReflection = new DemoReflection();
            System.out.printf("%s%nLet’s create an object.%n", SEP);
            demoReflection.createObject();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
