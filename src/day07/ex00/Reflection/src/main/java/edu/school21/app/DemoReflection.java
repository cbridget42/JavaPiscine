package edu.school21.app;

import edu.school21.classes.Car;
import edu.school21.classes.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public class DemoReflection {
    Scanner sc = new Scanner(System.in);
    Field[] fields;
    Method[] methods;
    Class<?> item;
    Object obj = null;

    public DemoReflection() {
        String name = sc.nextLine();
        switch (name) {
            case "User":
                this.item = User.class;
                break;
            case "Car":
                this.item = Car.class;
                break;
            default:
                throw new RuntimeException("No such class!");
        }
        this.fields = this.item.getDeclaredFields();
        this.methods = this.item.getDeclaredMethods();
        printFieldsAndMethods();
    }

    private void printFieldsAndMethods() {
        System.out.printf("%s%nfields:%n", Program.SEP);
        //Field[] fields = this.item.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println("   " + fields[i].getType().getSimpleName() + " " + fields[i].getName());
        }
        System.out.println("methods:");
        //Method[] methods = this.item.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.printf("   %s %s(", methods[i].getReturnType().getSimpleName(),
                    methods[i].getName());
            Parameter[] parameters = methods[i].getParameters();
            for (int j = 0; j < methods[i].getParameterCount(); j++) {
                String comma = "";
                if (j + 1 != methods[i].getParameterCount()) {
                    comma = ", ";
                }
                System.out.printf("%s%s", parameters[j].getType().getSimpleName(), comma);
            }
            System.out.println(")");
        }
    }

    public void createObject() throws Exception {
        Class<?>[] typeParms = new Class<?>[fields.length];
        Object[] objects = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            typeParms[i] = fields[i].getType();
            System.out.printf("%s:%n", fields[i].getName());
            switch (typeParms[i].getSimpleName()) {
                case "int":
                    objects[i] = sc.nextInt();
                    sc.nextLine();
                    break;
                case "double":
                    objects[i] = sc.nextDouble();
                    break;
                case "boolean":
                    objects[i] = sc.nextBoolean();
                    break;
                case "long":
                    objects[i] = sc.nextLong();
                    break;
                case "String":
                    objects[i] = sc.nextLine();
                    break;
                default:
                    throw new RuntimeException("unexpected type in class field");
            }
        }
        obj = item.getConstructor(typeParms).newInstance(objects);
        System.out.printf("Object created: %s%n", obj.toString());
    }
}
