package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.model.SpecialOffer;
import org.lessons.springlamiapizzeriacrud.service.PizzaService;
import org.lessons.springlamiapizzeriacrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/special_offer")
public class SpecialOfferController {

    @Autowired
    SpecialOfferService specialOfferService;

    @Autowired
    PizzaService pizzaService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model) {
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setStartingDate(LocalDateTime.now());
        specialOffer.setExpireDate(LocalDateTime.now().plusMonths(1));

        if (id.isPresent()) {
            try {
                Pizza pizza = pizzaService.getPizzaById(id.get());
                specialOffer.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        model.addAttribute("specialOffer", specialOffer);


        return "/special_offers/create";
    }
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute SpecialOffer formSpecialOffer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/special_offers/create";
        }
        SpecialOffer createdSpecialOffer = specialOfferService.create(formSpecialOffer);
        return "redirect:/pizzas/pizza_detail/" + Integer.toString(createdSpecialOffer.getPizza().getId());
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "offerId") Integer id, Model model) {

        try {
            SpecialOffer specialOfferToEdit = specialOfferService.getSpecialOfferById(id);
            model.addAttribute("specialOffer", specialOfferToEdit);
            return "/special_offers/edit";
        } catch (PizzaNotFoundException e) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "Offerta con id " + id + " non trovata.");
        }

    }

}
