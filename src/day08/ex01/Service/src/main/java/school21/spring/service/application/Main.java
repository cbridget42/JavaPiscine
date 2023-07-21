package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.repositories.UsersRepository;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository<?> usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository<?> usersRepositoryTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        String email = "kek@mail.com";
        System.out.println("---test hikariDataSource---".toUpperCase());
        test(usersRepository, email);
        System.out.println();
        System.out.println("---test driverManagerDataSource---".toUpperCase());
        test(usersRepositoryTemplate, email);
    }

    private static void test(UsersRepository<?> repository, String email) {
        System.out.println("find all:");
        System.out.println(repository.findAll());
        System.out.println();
        System.out.println(String.format("find by email %s:", email));
        System.out.println(repository.findByEmail(email));
    }
}
