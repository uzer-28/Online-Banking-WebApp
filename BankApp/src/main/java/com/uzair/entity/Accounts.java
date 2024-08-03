package com.uzair.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {

	@Id
	@Column(name = "account_no")
	private int accountNo;
	
	@Column(name = "bank_name")
	private String bankName;		
	
	private Long pin;
	
	private double balance;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	
}
