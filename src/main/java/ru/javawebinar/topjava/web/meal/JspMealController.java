package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RequestMapping("/meals")
@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("/create")
    public String save(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        int id = getParsedId(request);
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteById(HttpServletRequest request) {
        int id = getParsedId(request);
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getFilteredMeals(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals",
                super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private int getParsedId(HttpServletRequest request) {
        String idStr = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(idStr);
    }

    @PostMapping
    public String getOnChange(HttpServletRequest request) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        String idStr = request.getParameter("id");
        if (idStr.isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(idStr));
        }
        return "redirect:/meals";
    }
}