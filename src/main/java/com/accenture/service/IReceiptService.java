package com.accenture.service;

import java.util.Date;
import java.util.List;

import com.accenture.model.Order;
import com.accenture.model.Product;

public interface IReceiptService {

	void addOrder(Order order);
	
	void editOrder(long orderId, List<Product> products, Date editDate);
	
	void removeOrder(long orderId, Date date);
}
