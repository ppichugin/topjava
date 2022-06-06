package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CollectionMealDao;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Dao<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new CollectionMealDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id), dateTime, description, calories);
        if (id.isEmpty()) {
            log.debug("doPost - added new meal");
            storage.add(meal);
        } else {
            log.debug("doPost - updated meal: {}", id);
            storage.update(meal);
        }
        List<MealTo> mealToList = notFilteredMealToList();
        request.setAttribute("mealList", mealToList);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet");
        String action = request.getParameter("action");
        String mealId = request.getParameter("id");
        if (action == null) {
            action = "list";
        }
        Meal meal = null;
        switch (action) {
            case "delete":
                int id = Integer.parseInt(mealId);
                log.debug("doGet - delete meal {}", id);
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "update":
                id = Integer.parseInt(mealId);
                log.debug("doGet - update meal {}", id);
                meal = storage.get(id);
                break;
            case "add":
                log.debug("doGet - add item");
                break;
            case "list":
                log.debug("doGet - list");
                request.setAttribute("mealList", notFilteredMealToList());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
            default:
                response.sendRedirect("meals");
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }

    private List<MealTo> notFilteredMealToList() {
        return MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }
}
