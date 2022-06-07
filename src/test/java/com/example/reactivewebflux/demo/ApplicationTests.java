package com.example.reactivewebflux.demo;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.reactivewebflux.demo.controller.CustomerController;
import com.example.reactivewebflux.demo.model.Customer;
import com.example.reactivewebflux.demo.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(CustomerController.class)
class ApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void saveCustomerTest() {
		Customer customerObject = new Customer(1,"Abhinay");
		Mono<Customer> customerMonoObject = Mono.just(new Customer(1,"Abhinay"));
		when(customerService.save(customerObject)).thenReturn(customerMonoObject);
		webTestClient.post().uri("/customer").body(customerMonoObject,Customer.class).exchange()
		.expectStatus().isOk();
	}

	public void getCustomersTest() {
		
		Flux<Customer> customerMonoObject = Flux.just(new Customer(1,"Abhinay"),new Customer(2,"Ram"),new Customer(3,"Veera"));
		when(customerService.getAllCustomer()).thenReturn(customerMonoObject);
		webTestClient.get().uri("/customer").exchange()
		.expectStatus().isOk().returnResult(Customer.class).getResponseBody();
	}
}
