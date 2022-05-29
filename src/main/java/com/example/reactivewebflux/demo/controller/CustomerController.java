package com.example.reactivewebflux.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactivewebflux.demo.model.Customer;
import com.example.reactivewebflux.demo.repo.CustomerRepository;
import com.example.reactivewebflux.demo.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	
	
	//Reactive Programming uses project reactor as its dependency.it is build on top of project reactors
	//Project reactors provide two constructs Mono and Flux
	//Flux will be used when we are dealing with one or more number of records
	//Mono will be used when we are dealing with zero or one record
	@GetMapping
	public Flux<Customer> getAllCustomerDetails(){
		return customerService.getAllCustomer();
	}
	
	@GetMapping("/getCustomerDetails/{id}")
	public Mono<Customer> getCustomerDetails(@PathVariable Integer customerId){
		return customerService.findById(customerId);
	}
	
	@PostMapping
	public Mono<Customer> saveCustomer(@RequestBody Customer customer) {
		return customerService.save(customer);
	}
	
	@PutMapping("/updateCustomer/{id}")
	public Mono<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable Integer customerId) {
		return customerService.update(customer,customerId);
	}
	
	@DeleteMapping("/deleteCustomer/{id}")
	public Mono<Void> deleteCustomer(@PathVariable Integer customerId) {
		return customerService.deleteById(customerId);

	}
	
}
