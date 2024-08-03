package com.uzair.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uzair.dto.AccountsDTO;
import com.uzair.entity.Accounts;
import com.uzair.entity.Users;
import com.uzair.repository.AccountsRepository;

@Service
public class AccountsService {

	@Autowired
	private AccountsRepository repo;
	
	public List<Accounts> getAccounts(Users user){
		return repo.findByUser(user);
	}

	public void save(AccountsDTO dto, Users user) {

		Accounts acct = new Accounts();
		acct.setAccountNo(dto.getAccountNo());
		acct.setPin(dto.getPin());      //encrypt the pin.
		acct.setBankName(dto.getBankName());
		acct.setUser(user);
		
		repo.save(acct);
	}
	
	public List<Object> checkAccount(int accountNo) {
		
		List<Object> obj = new ArrayList<>();
		
		Accounts acct = repo.findByAccountNo(accountNo);
		
		if(acct != null) {
			Users user = acct.getUser();
			obj.add(acct);
			obj.add(user);
		}
		
		return obj;
	}
	
	public Accounts findByAccountNo(int accountNo) {
		return repo.findByAccountNo(accountNo);
	}
	
	public void paymentSave(Accounts acct) {
		repo.save(acct);
	}
	
	public boolean verify(int accountNo, Long pin) {
		Accounts acct = repo.findByAccountNoAndPin(accountNo, pin);
		if(acct == null)
			return false;
		return true;
	}
	
	public Users findUserByAccountNo(int accountNo) {
		
		return repo.findByAccountNo(accountNo).getUser();
	}
}





