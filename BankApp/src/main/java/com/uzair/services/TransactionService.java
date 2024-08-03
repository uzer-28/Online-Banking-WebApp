package com.uzair.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uzair.entity.Accounts;
import com.uzair.entity.Transaction;
import com.uzair.entity.Users;
import com.uzair.payment.Payment;
import com.uzair.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private Payment payment;

	public Boolean save(Transaction transaction, Users user, Users receiverUser) {

		Accounts acct = accountsService.findByAccountNo(transaction.getAccountNo());
		Accounts acct1 = accountsService.findByAccountNo(transaction.getReceiverAccountNo());

		transaction.setBankName(acct.getBankName());
		transaction.setReceiverBankName(acct1.getBankName());
		transaction.setSenderUser(user);
		transaction.setReceiverUser(receiverUser);
		transaction.setDate(LocalDate.now());

		boolean res = payment.payment(transaction.getAccountNo(), transaction.getReceiverAccountNo(),
				transaction.getAmount());

		if (res) {
			transaction.setStatus("Complete");
			repo.save(transaction);
		} else {
			transaction.setStatus("Failed");
			repo.save(transaction);
		}
		return res;
	}

	public void saveToDb(Transaction transaction, Users user, Users receiverUser) {

		Accounts acct = accountsService.findByAccountNo(transaction.getAccountNo());
		Accounts acct1 = accountsService.findByAccountNo(transaction.getReceiverAccountNo());

		transaction.setBankName(acct.getBankName());
		transaction.setReceiverBankName(acct1.getBankName());
		transaction.setSenderUser(user);
		transaction.setReceiverUser(receiverUser);
		transaction.setDate(LocalDate.now());
		transaction.setStatus("Failed");
		repo.save(transaction);
	}

	
	public List<Transaction> getTransactions(Users user){
		return repo.findBySenderUserOrReceiverUser(user, user);
	}
}
