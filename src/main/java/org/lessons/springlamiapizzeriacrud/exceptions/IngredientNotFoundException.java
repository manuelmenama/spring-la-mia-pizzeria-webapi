package org.lessons.springlamiapizzeriacrud.exceptions;

public class IngredientNotFoundException extends RuntimeException{

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
