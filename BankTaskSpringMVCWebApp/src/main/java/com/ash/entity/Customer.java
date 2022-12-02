package com.ash.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

//@Table(name = "MySQL Table Name if Different to Class)
public class Customer {
	
	@Id
	private int accountId;
	
	private String customerName;
	private String address;
	private String phoneNumber;
	private double balance;
	//@Column(name = "name of column in mysql if its different)
	private String userPassword; 
}
