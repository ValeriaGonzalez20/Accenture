package com.accenture.util;

import java.util.ArrayList;
import java.util.List;

import com.accenture.model.Product;

public class Utils {
	
	public static final  int DELIVERY_VALUE = 3000;
	
	public static final  int DELIVERY_THRESHOLD = 100000;
	
	public static final double TAXES_VALUE = 0.19;

	public static List<Product> createProductsLowerThan70000() {
		
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Fried Chicken", 20000, "FC001"));
		products.add(new Product(2, "Burger", 25000, "BB002"));
		products.add(new Product(3, "Pizza", 19000, "PP003"));
		products.add(new Product(4, "Coca-Cola Light", 3500, "CL004"));
		
		return products;
	}
	
	public static List<Product> createProductsGreaterThan100000() {
		
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Shrimp", 30000, "SH01"));
		products.add(new Product(2, "Octopus", 45000, "OC002"));
		products.add(new Product(3, "Fish", 17000, "PP003"));
		products.add(new Product(4, "Fresh Meat", 30000, "MT004"));
		products.add(new Product(5, "Vegetables", 22000, "VG005"));
		
		return products;
	}
	
	public static List<Product> createProductsLowerThan40000() {
		
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Fried Chicken", 20000, "FC001"));
		products.add(new Product(3, "Pizza", 19000, "PP003"));
		
		return products;
	}
}
