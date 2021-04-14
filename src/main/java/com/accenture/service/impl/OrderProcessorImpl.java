package com.accenture.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.model.Client;
import com.accenture.model.Order;
import com.accenture.model.Product;
import com.accenture.service.IOrderProcessor;
import com.accenture.service.IReceiptService;
import com.accenture.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderProcessorImpl implements IOrderProcessor {

	private static int orderCounter = 0;
	
	@Autowired
	private IReceiptService iReceiptService;
	
	/**
	 * Creates an Order with the specified products, also
	 * creates the related receipt
	 * @param hour
	 * @return Date object
	 */
	@Override
	public void createOrder(Client client, List<Product> products, Date date) {
		
		long total = getProductsTotal(products);
		
		long delivery_value = 0;
		
		if(total < Utils.DELIVERY_THRESHOLD) {
			delivery_value = Utils.DELIVERY_VALUE;
		}
		
		orderCounter += 1;
		Order order = new Order(orderCounter, client, total, delivery_value, date, products);
		
		log.info("Order created: " + order);
		
		iReceiptService.addOrder(order);	
		
	}

	/**
	 * Order edition, validating the creation date
	 * @param hour
	 * @return Date object
	 */
	@Override
	public void editOrder(long orderId, List<Product> products, Date editDate) {
		iReceiptService.editOrder(orderId, products, editDate);
	}

	/**
	 * Delete an Order, validating the creation date
	 * @param hour
	 * @return Date object
	 */
	@Override
	public void deleteOrder(long orderId, Date date) {
		iReceiptService.removeOrder(orderId, date);
	}

	/**
	 * Calculate the Products list total value
	 * @param products
	 * @return total
	 */
	private long getProductsTotal(List<Product> products) {
		long total = 0;
		
		if(products != null && products.size() > 0) {
			for(Product item : products) {
				total += item.getPrice();
			}
		}
		
		return total;
	}
}
