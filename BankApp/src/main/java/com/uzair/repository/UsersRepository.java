package com.uzair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uzair.entity.Users;
import java.util.List;


public interface UsersRepository extends JpaRepository<Users, Integer>{

	public Users findByEmail(String email);
	
	public Users findById(int id);
}
