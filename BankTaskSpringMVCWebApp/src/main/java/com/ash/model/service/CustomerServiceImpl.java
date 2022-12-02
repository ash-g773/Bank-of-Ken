package com.ash.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ash.entity.Customer;
import com.ash.model.persistence.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao dao;
	
	//you can also change this to directly return a customer
	@Override
	public boolean loginCheck(Customer customer) {
		
		try {
			Customer cust = dao.findByAccountIdAndUserPassword(customer.getAccountId(), customer.getUserPassword());
			if (cust!=null)
				return true;
			else
				return false;
		} catch (Exception e) { //if the dao query throws an error
			return false;
		}

	}
	
	@Override
	public Customer searchById(int id) {
		return dao.findById(id).orElse(null);
	}
	
	//this can also be return type customer?
	@Override
	public boolean transferFunds(int id1, int id2, double amount) {
		if(dao.updateBalance(id1, amount) > 0 && dao.updateBalance(id2, -amount) > 0)
			return true;
		else
			return false;
	}

}
