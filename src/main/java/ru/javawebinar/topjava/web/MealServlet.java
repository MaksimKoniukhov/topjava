package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("init {}", appCtx);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("destroy {}", appCtx);
        appCtx.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int userId = SecurityUtil.authUserId();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(req);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                resp.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(userId, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(req));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealForm.jsp").forward(req, resp);
                break;
            case "all":
            default:
                log.info("getAll");
                req.setAttribute("meals", mealRestController.getAll());
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("action").equals("filter")) {
            List<MealWithExceed> mealWithExceeds = mealRestController
                    .getAll(req.getParameter("dateFrom").isEmpty() ? null : LocalDate.parse(req.getParameter("dateFrom"))
                            , req.getParameter("dateTo").isEmpty() ? null : LocalDate.parse(req.getParameter("dateTo"))
                            , req.getParameter("timeFrom").isEmpty() ? null : LocalTime.parse(req.getParameter("timeFrom"))
                            , req.getParameter("timeTo").isEmpty() ? null : LocalTime.parse(req.getParameter("timeTo")));
            req.setAttribute("meals", mealWithExceeds);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else {
            String id = req.getParameter("id");

            Meal meal = new Meal(SecurityUtil.authUserId(), id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(req.getParameter("dateTime")),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories")));

            if (meal.isNew()) {
                log.info("Create {}", meal);
                mealRestController.create(meal);
                resp.sendRedirect("meals");
            } else {
                log.info("Update {}", meal);
                mealRestController.update(meal, meal.getId());
                resp.sendRedirect("meals");
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
