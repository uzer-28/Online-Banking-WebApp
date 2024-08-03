package com.uzair.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uzair.entity.Accounts;
import com.uzair.entity.Transaction;
import com.uzair.entity.Users;
import com.uzair.services.AccountsService;
import com.uzair.services.TransactionService;
import com.uzair.services.UsersService;

@Controller
public class TransactionController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private TransactionService transactionService;

	@GetMapping("/payUser")
	public String getUser(Model model, Principal principal) {
		
		Users user = usersService.findByEmail(principal.getName());
		model.addAttribute("name", user.getName());
		return "payToUser";
	}
	
	@PostMapping("/checkAccount")
	public String checkAccount(@RequestParam int accountNo, RedirectAttributes redirect, 
			Model model, Principal principal, Transaction transaction) {
		
		Users loggedUser = usersService.findByEmail(principal.getName());
		model.addAttribute("name", loggedUser.getName());

		List<Object> obj = accountsService.checkAccount(accountNo);
		
		if(obj == null || obj.isEmpty()) {
			model.addAttribute("message", "Account doesn't exists.");
			return "payToUser";
		}
		
		Accounts acct = (Accounts) obj.get(0);
		Users user = (Users) obj.get(1);		
		
		if(user.getEmail().equals(principal.getName())) {
			model.addAttribute("message", "To transfer between bank, please go to Self Transfer.");
			return "payToUser";
		}
		
		List<Accounts> list = accountsService.getAccounts(loggedUser);
		
		model.addAttribute("transaction", transaction);
		model.addAttribute("account", acct);
		model.addAttribute("toName", user.getName());
		model.addAttribute("list", list);
		
		return "payToUser";
	}
	
	
	@PostMapping("/proceedToPay")
	public String proceedToPay(@ModelAttribute Transaction transaction, Principal principal, Model model,RedirectAttributes redirect) {
		
//		Users loggedUser = usersService.findByEmail(principal.getName());
//		
//		boolean res = transactionService.save(transaction, loggedUser);
//		
//		if(res) {
//			redirect.addFlashAttribute("success", "Successfully sent Rs. " + transaction.getAmount());
//		}
//		else {
//			redirect.addFlashAttribute("error", "Insufficient funds.");
//		}
		
		model.addAttribute("transaction", transaction);
		return "enterPin";
	}
	
	@PostMapping("/payToUser")
	public String payToUser(@ModelAttribute Transaction transaction, @RequestParam("pin") String pin_, RedirectAttributes redirect, Principal principal) {
	
		Long pin = (long) Integer.parseInt(pin_);
		
		boolean res = accountsService.verify(transaction.getAccountNo(), pin);
		
		Users loggedUser = usersService.findByEmail(principal.getName());
		Accounts acct = accountsService.findByAccountNo(transaction.getReceiverAccountNo());
		Users receiverUser = acct.getUser();
		
		if(res) {
			
			boolean res1 = transactionService.save(transaction, loggedUser, receiverUser);
			
			if(res1) {
				redirect.addFlashAttribute("success", "Successfully sent Rs. " + transaction.getAmount());
			}
			else {
				redirect.addFlashAttribute("error", "Insufficient funds.");
			}
			return "redirect:/home";
		}
		else {
			transactionService.saveToDb(transaction, loggedUser, receiverUser);
			redirect.addFlashAttribute("message", "Incorrect pin. Please try again.");
			return "redirect:/payUser";
		}
	}
	
	@GetMapping("/transactions")
	public String transactions(Principal principal, Model model) {
		
		Users loggedUser = usersService.findByEmail(principal.getName());
		List<Transaction> transactions = transactionService.getTransactions(loggedUser);
		model.addAttribute("transactions",transactions);
		model.addAttribute("loggedUser", loggedUser);
		return "transaction";
	}
	
	@GetMapping("/checkBalance")
	public String checkBalance(Principal principal, Model model) {
		
		Users loggedUser = usersService.findByEmail(principal.getName());
		List<Accounts> list = accountsService.getAccounts(loggedUser);
		model.addAttribute("list", list);
		model.addAttribute("name", loggedUser.getName());
		
		return "checkBalance";
	}
	
	@PostMapping("/checkBalance")
	public String balance(@RequestParam int accountNo, @RequestParam("pin") String pin_, Principal principal, Model model, RedirectAttributes redirect) {
		
		Long pin = (long) Integer.parseInt(pin_);
		Users loggedUser = usersService.findByEmail(principal.getName());
		List<Accounts> list = accountsService.getAccounts(loggedUser);
		model.addAttribute("list", list);
		model.addAttribute("name", loggedUser.getName());
		
		boolean res = accountsService.verify(accountNo, pin);
		if(res) {
			Accounts balance = accountsService.findByAccountNo(accountNo);
			model.addAttribute("balance", balance);
			return "checkBalance"; 
		}
		else {
			redirect.addFlashAttribute("message", "Incorrect pin entered.");
			return "redirect:/checkBalance";
		}
	}
}
