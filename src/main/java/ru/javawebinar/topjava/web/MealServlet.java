package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealMemoryDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private static final ConcurrentMap<Integer, Meal> MAP_MEALS = new ConcurrentHashMap<>();
    private static final MealDao DAO = new MealMemoryDaoImpl();
    private static final String lIST_MEALS = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";

    public static ConcurrentMap<Integer, Meal> getMapMeals() {
        return MAP_MEALS;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        List<Meal> meals = Arrays.asList(
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(ATOMIC_INTEGER.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        meals.forEach(meal -> MAP_MEALS.put(meal.getId(), meal));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = req.getParameter("action");

        if (("edit").equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = DAO.getMealById(mealId);
            req.setAttribute("meal", meal);
        } else if (("delete").equalsIgnoreCase(action)) {
            forward = lIST_MEALS;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            DAO.deleteMeal(mealId);
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(DAO.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealWithExceeds);
        } else if (("insert").equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
        } else {
            System.out.println("qwerty");
            forward = lIST_MEALS;
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(DAO.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealWithExceeds);
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meal");

        req.setCharacterEncoding("UTF-8");

        String mealId = req.getParameter("mealId");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(ATOMIC_INTEGER.incrementAndGet(), dateTime, description, calories);
            DAO.addMeal(meal);
        } else {
            Meal meal = new Meal(Integer.parseInt(mealId), dateTime, description, calories);
            DAO.updateMeal(meal);
        }
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(DAO.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealList", mealWithExceeds);
        req.getRequestDispatcher(lIST_MEALS).forward(req, resp);
    }
}
