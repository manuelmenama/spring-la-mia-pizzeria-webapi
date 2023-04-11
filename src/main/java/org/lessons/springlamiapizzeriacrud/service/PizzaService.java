package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    public Pizza createPizza(Pizza formPizza) {

        Pizza pizzaToStorage = new Pizza();
        pizzaToStorage.setName(formPizza.getName());
        pizzaToStorage.setPrice(formPizza.getPrice());
        pizzaToStorage.setImageLink(formPizza.getImageLink());
        pizzaToStorage.setDescription(formPizza.getDescription());

        Set<Ingredient> formIngredient = getPizzaIngredients(formPizza);
        pizzaToStorage.setIngredients(formIngredient);

        pizzaToStorage.setCreatedAt(LocalDateTime.now());
        pizzaToStorage.setUpdatedAt(LocalDateTime.now());

        return pizzaRepository.save(pizzaToStorage);

    }

    private Set<Ingredient> getPizzaIngredients(Pizza formPizza) {
        Set<Ingredient> formIngredient = new HashSet<>();
        for (Ingredient i:
             formPizza.getIngredients()) {
            formIngredient.add(ingredientRepository.findById(i.getId()).orElseThrow());
        }
        return formIngredient;
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws PizzaNotFoundException{
        Pizza pizzaToUpdate = getPizzaById(id);
        pizzaToUpdate.setName(formPizza.getName());
        pizzaToUpdate.setPrice(formPizza.getPrice());
        pizzaToUpdate.setImageLink(formPizza.getImageLink());
        pizzaToUpdate.setDescription(formPizza.getDescription());

        Set<Ingredient> formIngredient = getPizzaIngredients(formPizza);
        pizzaToUpdate.setIngredients(formIngredient);

        pizzaToUpdate.setUpdatedAt(LocalDateTime.now());

        return pizzaRepository.save(pizzaToUpdate);

    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public List<Pizza> getSearchedPizzas(String keyword) {
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza getPizzaById(Integer id) throws PizzaNotFoundException{
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public boolean deletePizzaById(Integer id) {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
