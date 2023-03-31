package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.time.LocalDateTime;
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
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("pizzaList", pizzaList);

        return "pizzas/index";
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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/pizzas/create";
        }

        Pizza pizzaToStorage = new Pizza();
        pizzaToStorage.setName(formPizza.getName());
        pizzaToStorage.setPrice(formPizza.getPrice());
        pizzaToStorage.setImageLink(formPizza.getImageLink());
        pizzaToStorage.setDescription(formPizza.getDescription());
        pizzaToStorage.setCreatedAt(LocalDateTime.now());
        pizzaToStorage.setUpdatedAt(LocalDateTime.now());

        pizzaRepository.save(pizzaToStorage);
        return "redirect:/pizzas/pizza_detail/" + pizzaToStorage.getId().toString();
    }



}
