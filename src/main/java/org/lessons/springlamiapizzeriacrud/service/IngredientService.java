package org.lessons.springlamiapizzeriacrud.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Ingredient getIngredientById(Integer id) {

        return ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public Ingredient update(Ingredient formIngredient) {
        Ingredient ingredientToUpdate = ingredientRepository.findById(formIngredient.getId()).orElseThrow(() -> new RuntimeException());
        ingredientToUpdate.setName(formIngredient.getName());
        ingredientToUpdate.setDescription(formIngredient.getDescription());
        return ingredientRepository.save(ingredientToUpdate);
    }
}
