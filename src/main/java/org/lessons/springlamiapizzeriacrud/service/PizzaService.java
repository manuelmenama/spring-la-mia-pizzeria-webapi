package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formPizza) {

        Pizza pizzaToStorage = new Pizza();
        pizzaToStorage.setName(formPizza.getName());
        pizzaToStorage.setPrice(formPizza.getPrice());
        pizzaToStorage.setImageLink(formPizza.getImageLink());
        pizzaToStorage.setDescription(formPizza.getDescription());
        pizzaToStorage.setCreatedAt(LocalDateTime.now());
        pizzaToStorage.setUpdatedAt(LocalDateTime.now());

        return pizzaRepository.save(pizzaToStorage);

    }
}
