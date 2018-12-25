package com.el.rest.survey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
//@Entity
@Embeddable
public class SurveeDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int surveeId;
	
	@OneToOne
	private Assignment assign;*/
	
	@Column(length=40)
	private String surveeName;
	
	private long surveeContact;
	
	private boolean isSurveeVoterIdAvl;
	
	private short surveeAge;
	
	@Column(length=200)
	private String surveeAddress;
	
	private boolean isSurveeEmployed;
}
