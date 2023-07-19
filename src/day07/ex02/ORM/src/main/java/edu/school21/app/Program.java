package edu.school21.app;

import edu.school21.models.Plane;
import edu.school21.models.User;
import edu.school21.orm.OrmManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class Program {
    public static final String PACKAGE = "edu.school21.models";

    public static void main(String[] args) {

        OrmManager ormManager = new OrmManager(PACKAGE, new EmbeddedDatabaseBuilder().build());
        System.out.println();
        User user = new User(-1L, "kek", "lol", 18);
        ormManager.save(user);
        Plane plane = new Plane(-1L, "plane42", "France", 1000.0);
        ormManager.save(plane);

        System.out.println();
        System.out.println(String.format("Find user by id %d: %s", user.getId(),
                ormManager.findById(user.getId(), User.class)));
        System.out.println(String.format("Find plane by id %d: %s", plane.getId(),
                ormManager.findById(plane.getId(), Plane.class)));

        System.out.println();
        user.setFirstName("Bill");
        user.setLastName("Family");
        user.setAge(42);
        ormManager.update(user);

        System.out.println();
        System.out.println(String.format("User after update: %s%n",
                ormManager.findById(user.getId(), User.class)));
    }
}
