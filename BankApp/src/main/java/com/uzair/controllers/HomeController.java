package com.uzair.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uzair.dto.UsersDTO;
import com.uzair.entity.Users;
import com.uzair.services.UsersService;

@Controller
public class HomeController {
	
	@Autowired
	private UsersDTO dto;
	
	@Autowired
	private UsersService service;

	@GetMapping("/")
	public String index() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/home";
		}
		
		return "index";
	}
		
	@GetMapping("/login")
	public String login() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/home";
		}
		
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/home";
		}
		
		model.addAttribute("userDTO", new UsersDTO());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute UsersDTO userDTO, Model model) {
		
		service.save(userDTO);
		
		model.addAttribute("success", "Sign Up Successful. Login to Continue.");
		
		return "login";
	}
	
	@GetMapping("/home")
	public String home(Model model, Principal principal) {
		
		if(principal != null) {
			Users user = service.findByEmail(principal.getName());
			model.addAttribute("name", user.getName());
			model.addAttribute("id", user.getId());
			
		}
		
		return "home";
	}
}
