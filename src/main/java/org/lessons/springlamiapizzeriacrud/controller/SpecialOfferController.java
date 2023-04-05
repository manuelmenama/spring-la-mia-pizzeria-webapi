package org.lessons.springlamiapizzeriacrud.controller;

import org.lessons.springlamiapizzeriacrud.service.PizzaService;
import org.lessons.springlamiapizzeriacrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/special_offer")
public class SpecialOfferController {

    @Autowired
    SpecialOfferService specialOfferService;

    @Autowired
    PizzaService pizzaService;

    
}
