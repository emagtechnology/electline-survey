package com.el.rest.dto;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String Name;
	
	private String role;
	
	@Email
	private String email;
	
	private String password;
	
	private boolean enabled = true;
	
}
