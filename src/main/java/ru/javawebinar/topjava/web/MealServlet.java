package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ConcurHashMapImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao storage;
    private List<MealTo> mealToList;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new ConcurHashMapImpl();
        mealToList = notFilteredMealToList();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), dateTimeFormatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal newItem = new Meal(dateTime, description, calories);
        if (id == null) {
            log.debug("doPost - added new item");
            storage.addItem(newItem);
        } else {
            log.debug("doPost - updated item: " + id);
            Meal existing = storage.getItem(Integer.parseInt(id));
            storage.deleteItem(existing.getId());
            storage.updateItem(newItem);
        }
        mealToList = notFilteredMealToList();
        request.setAttribute("mealList", mealToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet");
        String action = request.getParameter("action");
        String mealId = request.getParameter("id");
        Integer id = null;
        Meal meal = null;
        if (action == null) {
            log.debug("doGet - list");
            request.setAttribute("mealList", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        } else if (!"add".equals(action)) {
            id = Integer.parseInt(mealId);
        }
        switch (action) {
            case "delete" -> {
                log.debug("doGet - delete item " + id);
                storage.deleteItem(id);
                mealToList = notFilteredMealToList();
                response.sendRedirect("meals");
                return;
            }
            case "update" -> {
                log.debug("doGet - update item " + id);
                meal = storage.getItem(id);
            }
            case "add" -> log.debug("doGet - add item");
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    private List<MealTo> notFilteredMealToList() {
        return MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }
}
