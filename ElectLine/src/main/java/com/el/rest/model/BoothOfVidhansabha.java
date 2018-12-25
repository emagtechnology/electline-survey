package com.el.rest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class BoothOfVidhansabha implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boothId;
	
	@OneToOne
	private VidhanSabhaOfIndia vidhanS;
	
	private int boothNumber;
	
	private String boothName;
	
	private boolean active = true;
		
}
