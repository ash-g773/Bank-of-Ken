package com.ash.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ash.entity.Customer;
import com.ash.model.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@RequestMapping("/")
	public ModelAndView loginPageInputController() {
		return new ModelAndView("LoginPage", "customer", new Customer());
	}
	
	//you can also do this method without using model attribute / setting a session attribute in the html - you can pass in http session
	//and set the session attribute in here
	@RequestMapping("/userLoginPage")
	public ModelAndView loginPageController(@ModelAttribute("customer") Customer customer, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (service.loginCheck(customer)) {
			Customer customerDetails = service.searchById(customer.getAccountId());
			modelAndView.addObject("customer", customerDetails); //adding the object to the model+view
			session.setAttribute("customer", customerDetails); //adding the customer attribute to the session
			modelAndView.setViewName("index"); //takes user through to the menu / main home page of the website
		} else {
			modelAndView.addObject("message", "Incorrect login details! Please try again");
			session.setAttribute("customer", new Customer()); //since we are expecting a customer attribute to be set, we just set an empty one here (?)
			modelAndView.setViewName("LoginPage"); //if its incorrect i.e. not found in database, then user remains on login page and error message is displayed on page
		}
		
		return modelAndView; //everything stored in here now ready for view to display
	}
	
	@RequestMapping("/showAccountDetailsPage")
	public ModelAndView showAccountDetailsPageController() {
//		Customer customerDetailed = service.searchById(customer.getAccountId());
//		ModelAndView modelAndView = new ModelAndView();
//		
//		modelAndView.addObject("customerDetailed", customerDetailed);
//		modelAndView.setViewName("ShowAccountDetails");
		
		return new ModelAndView("ShowAccountDetails");
	}
	
	@RequestMapping("/transferFundsInputPage")
	public ModelAndView transferFundsInputPageController() {
		return new ModelAndView("TransferFundsInputPage");
	}
	
	@RequestMapping("/transferFunds")
	public ModelAndView transferFundsController(@RequestParam("transferId") Integer transferId, @RequestParam("transferAmount") Double transferAmount, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Customer customerDetails = (Customer) session.getAttribute("customer");
		
		//you can do the following checks in the service method transfer funds as well
		//checking if account exists
		if(service.searchById(transferId)==null) {
			modelAndView.addObject("message", "Account with ID " + transferId + " does not exist!");
			modelAndView.setViewName("TransferFundsInputPage");
		//checking if user account has sufficient funds
		} else if (customerDetails.getBalance() < transferAmount) {
			modelAndView.addObject("message", "Insufficient funds!");
			modelAndView.setViewName("TransferFundsInputPage");		
		//checking amount is logical
		} else if (transferAmount <= 0) {
			modelAndView.addObject("message", "Please transfer an amount more than 0!");
			modelAndView.setViewName("TransferFundsInputPage");	
		//confirming + carrying out transaction once these are confirmed
		} else {
			service.transferFunds(customerDetails.getAccountId(), transferId, transferAmount);
			modelAndView.addObject("message", "Succesfully transferred! Transfer again?");
			session.setAttribute("customer", customerDetails); //resetting the customer attribute values cos now its updated
			modelAndView.setViewName("TransferFundsInputPage");			
		}
		
		return modelAndView;
	}
	
}
