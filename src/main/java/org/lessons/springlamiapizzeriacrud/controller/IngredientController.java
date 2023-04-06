package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
