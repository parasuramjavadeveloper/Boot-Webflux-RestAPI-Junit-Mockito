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
class CustomerControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void saveCustomerTest() {
		Customer customerObject = new Customer(1,"Veera");
		Mono<Customer> customerMonoObject = Mono.just(new Customer(1,"Veera"));
		when(customerService.save(customerObject)).thenReturn(customerMonoObject);
		webTestClient.post().uri("/customer").body(customerObject,Customer.class).exchange()
		.expectStatus().isOk();
	}

	@Test
	public void getCustomersTest() {
		Flux<Customer> customerMonoObject = Flux.just(new Customer(1,"Veera"),new Customer(2,"Ram"),new Customer(2,"Bhavi"));
		when(customerService.getAllCustomer()).thenReturn(customerMonoObject);
		webTestClient.get().uri("/customer").exchange()
		.expectStatus().isOk().returnResult(Customer.class).getResponseBody();
	}

	@Test
	public void updateCustomerTest() {
		Customer customerObject = new Customer(2,"Bhavi");
		Mono<Customer> customerMonoObject = Mono.just(new Customer(2,"Chinni"));
		when(customerService.update(customerObject,customerObject.getId())).thenReturn(customerMonoObject);
		webTestClient.put().uri("/customer").body(customerObject,Customer.class).exchange()
				.expectStatus().isOk();
	}

	@Test
	public void deleteCustomerTest() {
		Customer customerObject = new Customer(2,"Bhavi");
		when(customerService.deleteById(customerObject.getId())).thenReturn(Mono.empty());
		webTestClient.delete().uri("/customer").exchange()
				.expectStatus().isOk();
	}
}
