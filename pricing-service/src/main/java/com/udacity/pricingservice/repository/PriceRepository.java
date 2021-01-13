package com.udacity.pricingservice.repository;

import com.udacity.pricingservice.model.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {

}
