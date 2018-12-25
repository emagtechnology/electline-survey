package com.el.rest.survey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
//@Entity
@Embeddable
public class SurveyQues implements Serializable{

	private static final long serialVersionUID = 1L;

/*	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int surveeQuesId;
	
	@OneToOne
	private SurveeDetail survee;*/
	
	
	String partyLeading;
	
	String bestCandidateArea;
	
	@ElementCollection
	Map<String, Integer> castEquation = new HashMap<>();
	
	int totalVoter;
	
	@ElementCollection
	Set<String> issues = new HashSet<>();
	
	@ElementCollection
	Set<String> suggestions = new HashSet<>();
	
	String partyWish;
	
	@ElementCollection
	Map<String, String> partyWiseCandidate = new HashMap<>();
	
	String bestCmCandidate;
	
	String proposedCm;
	
	String bestPm;
	
	@ElementCollection
	Map<String, Long> influenceVoter = new HashMap<>();
	
	@ElementCollection
	Map<String, String> rebelParty = new HashMap<>();
	
	String bestMedia;
	
	@ElementCollection
	Set<String> freeComments = new HashSet<>();
	
}
