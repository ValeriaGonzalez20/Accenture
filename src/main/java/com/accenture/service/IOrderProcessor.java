package com.accenture.service;

import java.util.Date;
import java.util.List;

import com.accenture.model.Client;
import com.accenture.model.Product;

public interface IOrderProcessor {

	void createOrder(Client client, List<Product> products, Date date);
	
	void editOrder(long orderId, List<Product> products, Date editDate);
	
	void deleteOrder(long orderId, Date date);
	
}
