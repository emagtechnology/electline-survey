package com.el.rest.survey;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.el.rest.service.Assignment;

import lombok.Data;

@Data
@Entity
public class SurveyMain implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int surveeQuesId;
	
	@OneToOne
	private Assignment assign;
	
	private String boothName;
	
	@Embedded
	private SurveeDetail surveeDetail;
	
	@ElementCollection
	@Embedded
	private Set<SurveeFamily> surveeFamily = new HashSet<>();
	
	@ElementCollection
	@Embedded
	private Set<SurveeRelative> surveeRelative = new HashSet<>();
	
	@Embedded
	private SurveyQues surveyQues;
	
	/*@OneToOne*/
	//@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ElementCollection
	private Set<DynamicQuesAns> dqVidhan = new HashSet<>();
	
	
}
