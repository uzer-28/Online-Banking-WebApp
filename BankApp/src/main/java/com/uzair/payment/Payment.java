package com.uzair.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uzair.entity.Accounts;
import com.uzair.services.AccountsService;

@Service
public class Payment {

	@Autowired
	private AccountsService service;
	
	public boolean payment(int accountNo, int receiverAccountNo, double amount) {
		
		Accounts sender = service.findByAccountNo(accountNo);
		Accounts receiver = service.findByAccountNo(receiverAccountNo);
		
		if(0 < sender.getBalance() && sender.getBalance() >= amount) {
			
			sender.setBalance(sender.getBalance() - amount);
			receiver.setBalance(receiver.getBalance() + amount);
			
			service.paymentSave(sender);
			service.paymentSave(receiver);
			return true;
		}
		else {
			return false;
		}
	}
}
