package com.el.rest.survey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
//@Entity
@Embeddable
public class SurveeFamily implements Serializable{

	private static final long serialVersionUID = 1L;

/*	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int surveeFamilyMemberId;
	
	@OneToOne
	private SurveeDetail survee;*/
	
	@Column(length=40)
	private String surveeFamilyMemberName;
	
	private long surveeFamilyMemberContact;
	
	private boolean isSurveeFamilyMemberVoterIdAvl;
	
	private short surveeFamilyMemberAge;
	
	private boolean isSurveeFamilyMemberEmployed;
}
