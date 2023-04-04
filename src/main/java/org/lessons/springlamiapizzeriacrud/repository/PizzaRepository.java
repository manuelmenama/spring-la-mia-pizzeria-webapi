package org.lessons.springlamiapizzeriacrud.repository;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    public List<Pizza> findByNameContainingIgnoreCase(String name);

    public List<Pizza> findByDescriptionLike(String description);



}
