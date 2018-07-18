package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2018, 7, 17, 9, 4, 35)
            , "Завтрак", 500);

    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2018, 7, 17, 13, 4, 0)
            , "lunch", 1000);

    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2018, 7, 17, 19, 0, 30)
            , "Ужин", 501);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user_id");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user_id").isEqualTo(expected);
    }
}
