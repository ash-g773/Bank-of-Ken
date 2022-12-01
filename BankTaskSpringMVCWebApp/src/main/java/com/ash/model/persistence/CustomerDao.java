package com.ash.model.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ash.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> { //<entity, entity's primary key type>
	
	Customer findByAccountIdAndUserPassword(int id, String password);
	
	@Modifying
	@Transactional
	@Query("update Customer set balance=balance-:amt where accountId=:id")
	int updateBalance(@Param("id") int id, @Param("amt") double amount);
}
