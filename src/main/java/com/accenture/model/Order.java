package com.accenture.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {

	private long orderId; // Random
	
	private Client clientId;

	private long total;
	
	private long delivery;
	
	private Date creationDate;
	
	private List<Product> products;
	
}
