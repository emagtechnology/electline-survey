package com.el.rest.service;

import java.util.Date;

import lombok.Data;

@Data
public class ResetPass {

	private String email;
	private int resetPassId;
	private Date poltyDoB;
	private String answer;
	private String newPassword;
	
}
