package com.accenture.model;

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
public class Receipt {

	private long receiptId;
	
	private Order order;
	
	private long orderTotal;
	
	private long delivery;
	
	private double taxes;
	
	private double receiptTotal;
	
	private String status;
}
