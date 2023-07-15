package edu.school21.app;

public class Program {
    public static final String SEP = "---------------------";

    public static void main(String[] args) {
        try {
            DemoReflection demoReflection = new DemoReflection();
            System.out.printf("%s%nLet’s create an object.%n", SEP);
            demoReflection.createObject();
            System.out.println(SEP);
            demoReflection.changeField();
            System.out.println(SEP);
            demoReflection.callMethod();
            demoReflection.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
