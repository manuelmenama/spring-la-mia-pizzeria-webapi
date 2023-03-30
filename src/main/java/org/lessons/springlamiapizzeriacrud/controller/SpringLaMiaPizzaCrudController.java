package org.lessons.springlamiapizzeriacrud.controller;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class SpringLaMiaPizzaCrudController {

    @Autowired
    PizzaRepository pizzaRepository;

    @GetMapping
    public String home(Model model, @RequestParam(name = "q") Optional<String> keyword) {

        List<Pizza> pizzaList;

        if(keyword.isEmpty()) {
            pizzaList = pizzaRepository.findAll();

        } else {
            pizzaList = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());

        }

        model.addAttribute("pizzaList", pizzaList);

        return "pizzas/home";
    }

    @GetMapping("/pizza_detail/{id}")
    public String pizza(Model model, @PathVariable(name = "id", required = false) int id){

        Optional<Pizza> p = pizzaRepository.findById(id);
        if(p.isPresent()) {
            model.addAttribute("pizzaSelected", p.get());
            return "pizzas/pizza_detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }


    }



}
