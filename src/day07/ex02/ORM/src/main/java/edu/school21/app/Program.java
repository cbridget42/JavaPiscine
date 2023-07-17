package edu.school21.app;

import edu.school21.orm.OrmManager;

public class Program {
    public static final String PACKAGE = "edu.school21.models.";

    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager(PACKAGE);
    }
}
