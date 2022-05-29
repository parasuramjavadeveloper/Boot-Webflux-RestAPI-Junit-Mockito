package com.example.reactivewebflux.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table
@Data
public class Customer {

	@Id
	private Integer id;
	@Column
	private String name;

	public Customer() {	

	}
	public Customer(int id, String name) {
		this.id=id;
		this.name=name;
	}
}
