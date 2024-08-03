package com.uzair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uzair.entity.Accounts;
import com.uzair.entity.Users;

import java.util.List;


@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

	public List<Accounts> findByUser(Users user);
	
	public Accounts findByAccountNo(int accountNo);
	
	public Accounts findByAccountNoAndPin(int accountNo, Long pin);
		
	public Accounts findByAccountNoAndUser(int accountNo, Users user);
}
