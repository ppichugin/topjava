package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public List<MealTo> mealToList;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealToList = MealsUtil.meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate))
                .values()
                .stream()
                .flatMap(mealForCurrentDay -> {
                    int sumOfDailyCalories = mealForCurrentDay.stream().mapToInt(Meal::getCalories).sum();
                    boolean isExceeded = sumOfDailyCalories > CALORIES_PER_DAY;
                    return mealForCurrentDay.stream()
                            .map(m -> new MealTo(m.getDateTime(), m.getDescription(), m.getCalories(), isExceeded));
                })
                .sorted(Comparator.comparing(MealTo::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setAttribute("mealList", mealToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
