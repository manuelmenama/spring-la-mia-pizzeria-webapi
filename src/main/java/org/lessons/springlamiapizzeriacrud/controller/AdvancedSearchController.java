package org.lessons.springlamiapizzeriacrud.controller;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/advanced_search")
public class AdvancedSearchController {
    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping
    public String advancedPage() {
        return "/pizzas/advanced_search";
    }

    @GetMapping("/search")
    public String advancedSearch(
            @PathVariable(name = "d") Optional<String> keywordDescription,
            //@PathVariable(name = "p") Optional<BigDecimal> price,
            Model model
            ) {

        /*if (keywordDescription.isEmpty() && keywordName.isEmpty() && price == null) {
            pizzaList = pizzaRepository.findAll();

        } else {*/

        List<Pizza> pizzaList;

        if (keywordDescription.isPresent()) {
            pizzaList = pizzaRepository.findByDescriptionLike(keywordDescription.get());
            model.addAttribute("pizzaList", pizzaList);
            return "redirect:/pizzas";
        } else {
            pizzaList = pizzaRepository.findAll();
            model.addAttribute("pizzaList", pizzaList);
            return "redirect:/pizzas";
        }

    }
}
