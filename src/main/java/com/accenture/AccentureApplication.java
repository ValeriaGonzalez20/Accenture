package com.accenture;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.accenture.model.Client;
import com.accenture.service.IOrderProcessor;
import com.accenture.service.IReceiptService;
import com.accenture.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class AccentureApplication implements CommandLineRunner {

	@Autowired
	private IOrderProcessor iOrderProcessor;
	
	@Autowired
	IReceiptService iReceiptService;
	
	public static void main(String[] args) {
		SpringApplication.run(AccentureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("********************************************** ");
		log.info("************ First Order creation ************ ");
		log.info("********************************************** ");

		Client client1 = new Client(12345, "John Snow", "carrera 11 # 14-08", "snow@gmail.com");
		iOrderProcessor.createOrder(client1, Utils.createProductsLowerThan70000(), createDate(4));
		
		log.info("First Order update [success]...");
		iReceiptService.editOrder(1, Utils.createProductsLowerThan40000(), createDate(5));
		
		log.info("First Order update [failed]...");
		iReceiptService.editOrder(1, Utils.createProductsGreaterThan100000(), createDate(11));
		
		log.info("First Order delete [failed]...");
		iReceiptService.removeOrder(1, createDate(24));
		
		log.info("********************************************** ");
		log.info("************ Second Order creation ************ ");
		log.info("********************************************** ");
		
		iOrderProcessor.createOrder(client1, Utils.createProductsLowerThan70000(), createDate(3));
		log.info("Second Order delete [success]...");
		iReceiptService.removeOrder(2, createDate(9));
		
	}

	/**
	 * Creates a Date object with an specific hour value
	 * @param hour
	 * @return Date object
	 */
	private Date createDate(int hour) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}
}
