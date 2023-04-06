package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;
}
