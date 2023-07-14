package edu.school21.app;

import edu.school21.classes.Car;
import edu.school21.classes.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public class DemoReflection {
    private final Scanner sc = new Scanner(System.in);
    private final Field[] fields;
    private final Method[] methods;
    private final Class<?> item;
    private Object obj = null;

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
        for (Field field : fields) {
            System.out.println("   " + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println("methods:");
        for (Method method : methods) {
            System.out.println(constructMethodSignature(method, true));
        }
    }

    public void createObject() throws Exception {
        Class<?>[] typeParms = new Class<?>[fields.length];
        Object[] objects = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            typeParms[i] = fields[i].getType();
            System.out.printf("%s:%n", fields[i].getName());
            objects[i] = getValueFromUser(typeParms[i].getSimpleName());
        }
        obj = item.getConstructor(typeParms).newInstance(objects);
        System.out.printf("Object created: %s%n", obj);
    }

    public void changeField() throws IllegalAccessException {
        boolean run = true;
        int i = 0;
        while (run) {
            System.out.println("Enter name of the field for changing:");
            String name = sc.nextLine();
            if ((i = isFieldName(name)) != -1) {
                run = false;
            } else {
                System.out.println("try again!");
            }
        }
        System.out.printf("Enter %s value:%n", fields[i].getType().getSimpleName());
        fields[i].setAccessible(true);
        fields[i].set(obj, getValueFromUser(fields[i].getType().getSimpleName()));
        System.out.printf("Object updated: %s%n", obj.toString());
    }

    private int isFieldName(String name) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private Object getValueFromUser(String type) {
        Object res;
        switch (type) {
                case "int":
                    res = sc.nextInt();
                    sc.nextLine();
                    break;
                case "double":
                    res = sc.nextDouble();
                    sc.nextLine();
                    break;
                case "boolean":
                    res = sc.nextBoolean();
                    sc.nextLine();
                    break;
                case "long":
                    res = sc.nextLong();
                    sc.nextLine();
                    break;
                case "String":
                    res = sc.nextLine();
                    break;
                default:
                    throw new RuntimeException("unexpected type");
        }
        return res;
    }

    public void callMethod() throws Exception {
        boolean run = true;
        int i = 0;
        while (run) {
            System.out.println("Enter name of the method for call:");
            String method = sc.nextLine();
            if ((i = checkIfMethodExists(method)) != -1) {
                run = false;
            } else {
                System.out.println("try again!");
            }
        }
        Object res;
        res = methods[i].invoke(obj, getParametersFromUser(methods[i].getParameters()));
        if (res != null) {
            System.out.printf("Method returned:%n%s%n", res);
        }
    }

    private Object[] getParametersFromUser(Parameter[] parameters) {
        Object[] res = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            String type = parameters[i].getType().getSimpleName();
            System.out.printf("Enter %s value:%n", type);
            res[i] = getValueFromUser(type);
        }
        return res;
    }

    private int checkIfMethodExists(String method) {
        for (int i = 0; i < methods.length; i++) {
            String methodSignature = constructMethodSignature(methods[i], false);
            if (methodSignature.equals(method)) {
                return i;
            }
        }
        return -1;
    }

    private String constructMethodSignature(Method method, boolean flag) {
        StringBuilder methodSignature = new StringBuilder(String.format("%s(", method.getName()));
        Parameter[] parameters = method.getParameters();
        for (int j = 0; j < method.getParameterCount(); j++) {
            String comma = "";
            if (j + 1 != method.getParameterCount()) {
                comma = ", ";
            }
            methodSignature.append(String.format("%s%s", parameters[j].getType().getSimpleName(), comma));
        }
        methodSignature.append(')');
        if (flag) {
            methodSignature.insert(0, String.format("   %s ", method.getReturnType().getSimpleName()));
        }
        return methodSignature.toString();
    }
}
