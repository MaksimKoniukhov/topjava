package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(SecurityUtil.authUserId(), LocalDateTime.now(), "lunch", 2000));
            mealRestController.getAll().forEach(System.out::println);
            mealRestController.getAll(LocalDate.of(2015, 5, 30), null, LocalTime.of(13, 0), null)
                    .forEach(System.out::println);
            mealRestController.delete(3);
            mealRestController.delete(6);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println(mealRestController.get(1));

//            авторизованный пользователь пробует изменить не свою еду(id еды ему не принадлежит)
            mealRestController.update(new Meal(SecurityUtil.authUserId(), 9, LocalDateTime.of(2015, 5, 30, 10, 0)
                    , "Завтрак", 500), 9);

            System.out.println(mealRestController.get(4));
        }
    }
}
