package com.uzair.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int transactionId;
	
	private LocalDate date;
	
	@Column(name = "receiver_name")
	private String receiverName;
	
	@Column(name = "receiver_account_no")
	private int receiverAccountNo;
	
	@Column(name = "receiver_bank_name")
	private String receiverBankName;
	
	private double amount;
	
	@Column(name = "payer_account_no")
	private int accountNo;
	
	@Column(name = "payer_bank_name")
	private String bankName;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "payers_id")
	private Users senderUser;
	
	
	@ManyToOne
    @JoinColumn(name = "receiver_id")
    private Users receiverUser;
	
	
	
}
