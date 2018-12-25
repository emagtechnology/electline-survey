    package com.el.rest.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Surveyor  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int survId;

	@Column(length=35)
	private String survName;
	
	@Column(length=100,unique=true)
	private String survEmail;
	
	//@Column(length=25)
	private String survPass;
	
	private long survContact=0;
	
	private long survAadhar=0;
	
	@Column(length=10)
	private String survPanNumber="";
	
	private boolean activeSurveyor = true;
	
	private int assignID = 0;
   
}