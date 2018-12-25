package com.el.rest.survey;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Embeddable
public class DynamicQuesAns {

	@OneToOne
	private DynamicQuesVidhanSabaha dQues;
	
	private String dAns; 
	
}
