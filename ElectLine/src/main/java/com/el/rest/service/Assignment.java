package com.el.rest.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.el.rest.dto.Surveyor;
import com.el.rest.model.VidhanSabhaOfIndia;

import lombok.Data;

@Entity
@Data
public class Assignment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int assignId;
	
	@OneToOne
	private Surveyor surveyor;
	
	/*@OneToOne
	private LokSabhaOfIndia lokSabha;*/
	
	@OneToOne
	private VidhanSabhaOfIndia vidhanSabha;
	
	String blockName;
	
	String gramPanchayatName;
	
	@ElementCollection
	Map<Integer, String> boothData =  new HashMap<>();
	
	
}
