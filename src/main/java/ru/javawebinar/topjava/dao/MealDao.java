package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    public void addMeal(Meal meal);

    public void deleteMeal(int mealId);

    public void updateMeal(Meal meal);

    public List<Meal> getMealList();

    public Meal getMealById(int mealId);
}
