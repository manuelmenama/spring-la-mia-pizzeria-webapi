package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.exceptions.IngredientNotFoundException;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("allIngredient", ingredientService.findAllIngredients());
        model.addAttribute("newIngredient", new Ingredient());
        return "/ingredients/index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(name="newIngredient") Ingredient ingredient,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("allIngredient", ingredientService.findAllIngredients());
            return "/ingredients/index";
        }
        ingredientService.create(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) throws IngredientNotFoundException {
        Optional<Ingredient> ingredientToUpdate = ingredientService.getIngredientById(id);
        if (ingredientToUpdate.isPresent()) {
            model.addAttribute("newIngredient", ingredientToUpdate);
            model.addAttribute("allIngredient", ingredientService.findAllIngredients());
            return "/ingredients/index";
        } else {
            throw new IngredientNotFoundException("Ingrediente con id " + id + " non trovato");
        }
    }
}
