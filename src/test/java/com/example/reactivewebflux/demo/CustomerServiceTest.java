package com.example.reactivewebflux.demo;

import com.example.reactivewebflux.demo.exception.CustomerNotFoundException;
import com.example.reactivewebflux.demo.model.Customer;
import com.example.reactivewebflux.demo.repo.CustomerRepository;
import com.example.reactivewebflux.demo.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setup(){
        customer=Customer.builder().id(1).name("veera").build();
    }
    @Test
    @DisplayName("JUnit test for saveCustomer method")
    public void givenCustomerSaveDetailsTest(){
        when(customerRepository.save(customer)).thenReturn(Mono.just(customer));
        Customer savedCustomer = customerService.save(customer).block();
        assertThat(savedCustomer).isNotNull();
    }
    @Test
    @DisplayName("JUnit test for getAllCustomers method")
    public void getAllCustomersTest(){
       Customer  customer1=Customer.builder().id(2).name("Ram").build();
        when(customerRepository.findAll()).thenReturn(Flux.just(customer, customer1));
        Flux<Customer> customerList = customerService.getAllCustomer();
        assertThat(customerList).isNotNull();
        //assertThat(customerList.distinct()).isEqualTo(2);
        StepVerifier.create(customerList.log()).expectNext(customer).expectNext(customer1).verifyComplete();
    }

    @Test
    @DisplayName("JUnit test for getCustomerByName method in case of customer not found")
    public void getCustomerByNameExceptionTest(){
        CustomerNotFoundException thrown = Assertions.assertThrows(CustomerNotFoundException.class,() -> customerService.findByName("XYZ"));
        Assertions.assertEquals("Customer not exists", thrown.getMessage());
    }

    @Test
    @DisplayName("JUnit test for getCustomerByName method")
    public void getCustomerByNameTest(){
        Mono<Customer> customer = customerService.findByName("Test");
        assertThat(customer).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getCustomerById method")
    public void getCustomerDetailsTest(){
        when(customerRepository.findById(1)).thenReturn(Mono.just(customer));
        Customer savedCustomer = customerService.findById(customer.getId()).block();
        assertThat(savedCustomer).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for updateCustomer method")
    public void updateCustomerTest(){
        when(customerRepository.findById(1))
                .thenReturn(Mono.just(customer));
        Mono<Customer> customerMonoObject = Mono.just(new Customer(1,"updatedVeera"));
        when(customerRepository.save(customer)).thenReturn(customerMonoObject);
        Customer  updatedCustomer = customerService.update(customer,customer.getId()).block();
        assertThat(updatedCustomer.getName()).isEqualTo("updatedVeera");
    }

    @Test
    @DisplayName("JUnit test for deleteCustomer method")
    public void deleteCustomerTest(){
        int customerId = 1;
        when(customerRepository.deleteById(customerId)).thenReturn(Mono.empty());
        customerService.deleteById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }
}
