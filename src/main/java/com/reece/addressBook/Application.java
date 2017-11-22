package com.reece.addressBook;

import com.reece.addressBook.controller.AddressBookApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);

		AddressBookApplication employeeList = (AddressBookApplication) app.getBean("addressBookApplication");
		employeeList.init();
	}
}
