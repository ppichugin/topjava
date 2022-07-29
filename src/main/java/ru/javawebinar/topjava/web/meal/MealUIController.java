package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Meal create(@RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam int calories) {
        return super.create(new Meal(null, LocalDateTime.parse(dateTime), description, calories));
    }
}