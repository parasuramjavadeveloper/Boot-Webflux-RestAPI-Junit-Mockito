package com.example.reactivewebflux.demo.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.reactivewebflux.demo.model.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}
