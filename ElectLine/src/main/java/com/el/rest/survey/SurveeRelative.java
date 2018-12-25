package com.el.rest.survey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
//@Entity
@Embeddable
public class SurveeRelative implements Serializable{

	private static final long serialVersionUID = 1L;

/*	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int surveeRelativeId;
	
	@OneToOne
	private SurveeDetail survee;*/
	
	@Column(length=40)
	private String surveeRelativeName;
	
	private long surveeRelativeContact;
	
	@Column(length=200)
	private String surveeRelativeAddress;
}
