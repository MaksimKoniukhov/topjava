package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(100004, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(100002, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(100004, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealList = service.getBetweenDates(LocalDate.of(2018, 7, 15)
                , LocalDate.of(2018, 7, 17), USER_ID);
        assertMatch(mealList, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealList = service.getBetweenDateTimes(LocalDateTime.of(2018, 7, 17, 8, 4, 35)
                , LocalDateTime.of(2018, 7, 17, 12, 10, 35), USER_ID);
        assertMatch(mealList, MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(USER_ID);
        assertMatch(mealList, MEAL2, MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL3);
        updated.setDescription("dinner");
        updated.setCalories(1500);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(100004, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        service.update(MEAL3, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, 7, 17, 11, 46, 35)
                , "newMeal", 2000);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), MEAL3, newMeal);
    }
}
