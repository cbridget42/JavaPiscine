package edu.school21.app;

import edu.school21.models.User;
import edu.school21.orm.OrmManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class Program {
    public static final String PACKAGE = "edu.school21.models";

    public static void main(String[] args) {

        OrmManager ormManager = new OrmManager(PACKAGE, new EmbeddedDatabaseBuilder().build());
        User user = new User(1L, "kek", "lol", 18);
        ormManager.save(user);
        User user2 = new User(1L, "ooo", "aaa", 22);
        ormManager.save(user2);
        System.out.println("id: " + user2.getId());
        //OrmManager ormManager = new OrmManager(PACKAGE, null);
    }
}
