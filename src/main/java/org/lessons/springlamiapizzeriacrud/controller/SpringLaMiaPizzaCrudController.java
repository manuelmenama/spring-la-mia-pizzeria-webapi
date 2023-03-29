package org.lessons.springlamiapizzeriacrud.controller;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class SpringLaMiaPizzaCrudController {

    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping
    public String home(Model model) {

        List<Pizza> pizzaList = pizzaRepository.findAll();

        model.addAttribute("pizzaList", pizzaList);

        return "pizzas/home";
    }



}
