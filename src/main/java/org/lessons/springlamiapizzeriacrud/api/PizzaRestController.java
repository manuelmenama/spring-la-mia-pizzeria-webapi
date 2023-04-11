package org.lessons.springlamiapizzeriacrud.api;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizzas")
public class PizzaRestController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public List<Pizza> pizzas(@RequestParam(name = "q")Optional<String> searchedName) {

        if (searchedName.isPresent()) {
            return pizzaService.getSearchedPizzas(searchedName.get());
        }

        return pizzaService.getAllPizzas();
    }



}
