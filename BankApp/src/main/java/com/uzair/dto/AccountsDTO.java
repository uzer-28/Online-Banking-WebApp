package com.uzair.dto;

import org.springframework.stereotype.Service;

import com.uzair.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDTO {

	private int accountNo;
	
	private String bankName;
	
	private Long pin;
	
	private Long confirmPin;
	
	private double balance;
	
	private Users user;
}
