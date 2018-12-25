package com.el.rest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class VidhanSabhaOfIndia implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int vidhanSabhaId;
	
	String vidhanSabhaName;
	
	@ManyToOne
	private LokSabhaOfIndia lokSabha;
}
