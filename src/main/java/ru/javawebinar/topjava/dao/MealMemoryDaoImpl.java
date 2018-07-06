package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.ArrayList;
import java.util.List;

public class MealMemoryDaoImpl implements MealDao {
    @Override
    public void addMeal(Meal meal) {
        MealServlet.getMapMeals().put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        MealServlet.getMapMeals().remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        MealServlet.getMapMeals().put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getMealList() {
        return new ArrayList<>(MealServlet.getMapMeals().values());
    }

    @Override
    public Meal getMealById(int mealId) {
        return MealServlet.getMapMeals().get(mealId);
    }
}
