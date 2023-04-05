package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.lessons.springlamiapizzeriacrud.model.SpecialOffer;
import org.lessons.springlamiapizzeriacrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialOfferService {

    @Autowired
    SpecialOfferRepository specialOfferRepository;

    public SpecialOffer create(SpecialOffer formSpecialOffer) {
        return specialOfferRepository.save(formSpecialOffer);
    }

    public SpecialOffer getSpecialOfferById(Integer id) {
        Optional<SpecialOffer> result = specialOfferRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException("Offerta con id " + id + " non trovata.");
        }
    }
}
