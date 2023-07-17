package edu.school21.orm;

import javax.sql.DataSource;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OrmManager {
    DataSource dataSource;

    public OrmManager(String packageName, DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            URI uri = OrmManager.class.getClassLoader()
                    .getResource(packageName.replace('.', '/'))
                    .toURI();
            Stream<Path> stream = Files.walk(Paths.get(uri), 1)
            stream.forEach(element -> {
                String str = element.toString();
                if (str.endsWith(".class")) {
                    str = str.substring(str.lastIndexOf('/') + 1, str.lastIndexOf('.'));
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(packageName + "." + str);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (clazz.isAnnotationPresent(OrmEntity.class)) {
                        createTable(clazz);
                    }
                }
            });
            stream.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void createTable(Class<?> clazz) {

    }
}
