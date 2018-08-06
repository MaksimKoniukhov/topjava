package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.assertMatch;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getByIdWithMeals() {
        Meal meal = service.getWithUser(MealTestData.MEAL1_ID);
        assertMatch(meal, MealTestData.MEAL1);

        User user = meal.getUser();
        UserTestData.assertMatch(user, UserTestData.USER);
    }
}
