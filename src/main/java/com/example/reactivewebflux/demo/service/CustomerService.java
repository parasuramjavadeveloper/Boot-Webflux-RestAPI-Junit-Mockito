package com.example.reactivewebflux.demo.service;

import java.time.Duration;

import com.example.reactivewebflux.demo.exception.CustomerNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactivewebflux.demo.model.Customer;
import com.example.reactivewebflux.demo.repo.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
	
	
	@Autowired
    private CustomerRepository customerRepository;

    public Flux<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

	public Mono<Customer> findById(Integer customerId) {
		return customerRepository.findById(customerId);
	}

	public Mono<Customer> findByName(String customerName) throws CustomerNotFoundException {
		if("XYZ".equals(customerName)) {
			throw new CustomerNotFoundException("Customer not exists");
		}
		Customer customer = new Customer(3,customerName);
		return Mono.just(customer);
	}

	public Mono<Customer> save(Customer customer) {
	
        return customerRepository.save(customer).delayElement(Duration.ofMillis(3000)).map(customerDetail -> {
        	Customer customerSave = new Customer();
    		BeanUtils.copyProperties(customer, customerSave);

            return customerSave;
        });
	}

	public Mono<Customer> update(Customer customer, Integer customerId) {
		return customerRepository.findById(customerId)
				.map((customerDetail) -> {
					BeanUtils.copyProperties(customer, customerDetail);
					return customerDetail;
				}).flatMap(customerDetail -> customerRepository.save(customerDetail));
	}

	public Mono<Void> deleteById(Integer customerId) {
		return customerRepository.deleteById(customerId);
	}

}
