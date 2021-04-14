package com.accenture.service.impl;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.accenture.model.Order;
import com.accenture.model.Product;
import com.accenture.model.Receipt;
import com.accenture.service.IReceiptService;
import com.accenture.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReceiptServiceImpl implements IReceiptService {

	private Map<Long, Receipt> receipts = new HashMap<>();
	
	/**
	 * Creates the Receipt and add the related Order
	 * @param order
	 */
	@Override
	public void addOrder(Order order) {
		
		Random rand = new Random();
		
		double taxes = getOrderTaxes(order);
		
		double receiptTotal = order.getTotal() + order.getDelivery() + taxes;
				
		Receipt receipt = new Receipt(rand.nextInt(10000), order, order.getTotal(), order.getDelivery(), taxes, receiptTotal, "ACTIVE");
		
		log.info("Receipt created: " + receipt);
		
		receipts.put(order.getOrderId(), receipt);
	}

	/**
	 * Edit the Order and Receipt, validating the creation dates
	 * @param orderId, products, editDate
	 */
	@Override
	public void editOrder(long orderId, List<Product> products, Date editDate) {
		Receipt receipt = receipts.get(orderId);
		
		if(receipt != null) {
			Order order = receipt.getOrder();
			
			long hours = getHoursBetweenDates(order.getCreationDate(), editDate);
			
			if(hours <= 5) {
				boolean isValid = validateOrderUpdate(order, products);
				
				if(isValid) {
					order.setProducts(products);

					long orderTotal = getProductsTotal(products);
					double taxes = orderTotal * Utils.TAXES_VALUE;
					
					order.setTotal(orderTotal);
					
					long deliveryValue = Utils.DELIVERY_VALUE;
					if(Utils.DELIVERY_THRESHOLD <= orderTotal) {
						order.setDelivery(0);
						receipt.setDelivery(0);
						deliveryValue = 0;
					}

					receipt.setOrderTotal(orderTotal);
					receipt.setTaxes(taxes);
					receipt.setOrder(order);
					receipt.setReceiptTotal(taxes + deliveryValue + orderTotal);
					
					receipts.put(orderId, receipt);
					
					log.info("Order updated: " + order);
					log.info("Receipt updated: " + receipt);
				} else {
					log.error("The value must be greater or equal to the previous order");
				}
			} else {
				log.info("Your order has been sent, is not possible to update it: " + orderId);
			}
		}
		
	}

	/**
	 * Remove the Order and Receipt, validating the creation dates
	 * @param orderId, editDate
	 */
	@Override
	public void removeOrder(long orderId, Date date) {
		Receipt receipt = receipts.get(orderId);
		
		if(receipt != null) {
			Order order = receipt.getOrder();
			long hours = getHoursBetweenDates(order.getCreationDate(), date);
			
			if(hours <= 12) {
				receipts.remove(orderId);
				log.info("Order removed: " + order);
				log.info("Receipt removed: " + receipt);
			} else {
				double charge = order.getTotal() * 0.10;
				receipt.setReceiptTotal(charge);
				receipts.put(orderId, receipt);
				log.info("The allowed time tfor canceling your order has expired, you will be charged with : " + charge);
			}
		} else {
			log.info("Order not found: " + orderId);
		}
		
	}

	/**
	 * Validate if the new Order price is valid
	 * @param order
	 * @param products
	 * @return
	 */
	private boolean validateOrderUpdate(Order order, List<Product> products) {
		
		List<Product> prevProducts = order.getProducts();
		long prevTotal = getProductsTotal(prevProducts);
		
		long newTotal = getProductsTotal(products);
		
		if(newTotal >= prevTotal) {
			return true;
		}
		
		return false;
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
	
	/**
	 * Calculates order taxes
	 * @param order
	 * @return taxes
	 */
	private double getOrderTaxes(Order order) {
		double taxes = 0;
		
		if(order != null && order.getTotal() > 0) {
			taxes = order.getTotal() * Utils.TAXES_VALUE;
		}
		
		return taxes;
	}
	
	/**
	 * Get the number of hours between two dates
	 * @param startDate
	 * @param endDate
	 * @return hours
	 */
	private long getHoursBetweenDates(Date startDate, Date endDate) {
		// convert them to ZonedDateTime instances
		ZonedDateTime start = ZonedDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
		ZonedDateTime end = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

		Duration total = Duration.ofMinutes(ChronoUnit.MINUTES.between(start, end));

		return total.toHours();
	}
	
}
