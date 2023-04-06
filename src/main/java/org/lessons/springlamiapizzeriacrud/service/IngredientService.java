package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient create(Ingredient formIngredient) {
        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredient.getName());
        ingredientToCreate.setDescription(formIngredient.getDescription());
        return ingredientRepository.save(ingredientToCreate);
    }
}
