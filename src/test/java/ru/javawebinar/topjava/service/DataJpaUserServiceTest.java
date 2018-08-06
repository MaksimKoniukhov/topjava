package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getByIdWithMeals() {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER);

        List<Meal> meals = user.getMeals();
        Collections.reverse(meals);
        MealTestData.assertMatch(meals, MealTestData.MEALS);
    }
}
