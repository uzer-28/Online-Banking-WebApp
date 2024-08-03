package com.uzair.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uzair.dto.AccountsDTO;
import com.uzair.entity.Accounts;
import com.uzair.entity.Users;
import com.uzair.services.AccountsService;
import com.uzair.services.UsersService;

@Controller
public class AccountsController {
	
	@Autowired
	private AccountsService service;
	
	@Autowired
	private UsersService userService;

	@GetMapping("/accounts")
	public String getAccounts(Principal principal, Model model, AccountsDTO dto) {
		
		Users user = userService.findByEmail(principal.getName());
		List<Accounts> accounts = service.getAccounts(user);
			
		if(!accounts.isEmpty()) {	
			model.addAttribute("accounts", accounts);
		}
		else {
			model.addAttribute("message", "Account yet not added, please add.");
		}
		model.addAttribute("name", user.getName());
		model.addAttribute("accountDTO", dto);
		return "accounts";
	}
	
	@PostMapping("/addAccount")
	public String addAccount(@ModelAttribute AccountsDTO dto, RedirectAttributes redirect, Principal principal) {
		
		Users user = userService.findByEmail(principal.getName());
		if(dto.getPin().equals(dto.getConfirmPin())) {
			service.save(dto, user);
			redirect.addFlashAttribute("message", "Account added successfully.");
			return "redirect:/accounts";
		}
		
		redirect.addFlashAttribute("error", "Pin doesn't match.");
		return "redirect:/accounts";
	}
}
