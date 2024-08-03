package com.uzair.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uzair.dto.UsersDTO;
import com.uzair.entity.Users;
import com.uzair.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

	@Autowired
	private UsersRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void save(UsersDTO dto) {
		
		Users user = new Users();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		repo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = repo.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or Password");
		}
				
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
	
	public Users findByEmail(String email) {
		return repo.findByEmail(email);
	}
	
}
