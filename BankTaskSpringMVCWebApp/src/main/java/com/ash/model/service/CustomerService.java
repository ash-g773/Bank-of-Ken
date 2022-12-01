package com.ash.model.service;

import com.ash.entity.Customer;

public interface CustomerService {
	
	boolean loginCheck(Customer customer);

	Customer searchById(int id);

	boolean transferFunds(int id1, int id2, double amount);
	
}
