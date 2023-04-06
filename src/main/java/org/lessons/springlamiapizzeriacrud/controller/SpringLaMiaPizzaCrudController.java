package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.springlamiapizzeriacrud.model.AlertMessage;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.service.IngredientService;
import org.lessons.springlamiapizzeriacrud.service.PizzaService;
import org.lessons.springlamiapizzeriacrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class SpringLaMiaPizzaCrudController {


    @Autowired
    PizzaService pizzaService;

    @Autowired
    SpecialOfferService specialOfferService;

    @Autowired
    IngredientService ingredientService;

    @GetMapping
    public String home(Model model, @RequestParam(name = "q") Optional<String> keyword) {

        List<Pizza> pizzaList;

        if(keyword.isEmpty()) {
            pizzaList = pizzaService.getAllPizzas();

        } else {
            pizzaList = pizzaService.getSearchedPizzas(keyword.get());
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("pizzaList", pizzaList);

        return "pizzas/index";
    }

    @GetMapping("/pizza_detail/{id}")
    public String pizza(Model model, @PathVariable(name = "id", required = false) int id) throws PizzaNotFoundException {

        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("pizzaSelected", pizza);
            return "pizzas/pizza_detail";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }


        /*Optional<Pizza> p = pizzaRepository.findById(id);
        if(p.isPresent()) {
            model.addAttribute("pizzaSelected", p.get());
            return "pizzas/pizza_detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }*/


    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allIngredients", ingredientService.findAllIngredients());

        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        boolean hasErrors = bindingResult.hasErrors();

        if (hasErrors) {
            model.addAttribute("allIngredients", ingredientService.findAllIngredients());
            return "/pizzas/create";
        } else {
            pizzaService.createPizza(formPizza);
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Elemento " + formPizza.getName() + " creato con successo."));
            return "redirect:/pizzas";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) throws PizzaNotFoundException {

        try {
            Pizza pizzaToEdit = pizzaService.getPizzaById(id);
            model.addAttribute("pizza", pizzaToEdit);
            model.addAttribute("allIngredients", ingredientService.findAllIngredients());
            return "pizzas/update";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }


    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allIngredients", ingredientService.findAllIngredients());
            return "pizzas/update";
        }

        try {
            Pizza pizzaToUpdate = pizzaService.updatePizza(formPizza, id);
            return "redirect:/pizzas/pizza_detail/" + id;
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza con id " + id + " non trovata.");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws PizzaNotFoundException{
        try {
            boolean success = pizzaService.deletePizzaById(id);
            if (success) {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Elemento " + id + " eliminato con successo."));
            } else {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.ERROR, "L'eliminazione dell'elemento con id " + id + " fallita."));
            }
        } catch (PizzaNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Elemento con id " + id + " non trovato"));
        }
        return "redirect:/pizzas";
    }

}
