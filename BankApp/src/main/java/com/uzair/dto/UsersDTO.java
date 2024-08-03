package com.uzair.dto;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

	private String name;
	private String email;
	
	private Long pin;
	private Long confirmPin;
	private String password;
}
