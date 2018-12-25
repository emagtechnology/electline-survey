package com.el.rest.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;

import lombok.Data;

@Data
public class SurvData {
	
	private String surveyorName;
	
	private String surveyorEmail;
	
	private String boothName;
	
	private String surveeName;

	private String surveeContact;
	
	private String surveeAge;
	
	private String surveeAddress;
	
	private String surveeEmployed;
	
	private String surveeVoterIdAvl;
	
	private String surveeRelativeName;
	
	private String surveeRelativeContact;
	
	private String surveeRelativeAddress;
	
	private StringBuilder surveeFamily;
	
	private StringBuilder surveeRelative;
		
	private String partyLeading;
	
	private String bestCandidateArea;
	
	private String castEquation;// = new HashMap<>();
	
	private int totalVoter;
	
	private String issues;
	
	private String suggestions;
	
	private String partyWish;
	
	private StringBuilder partyWiseCandidate;// = new HashMap<>();
	
	private String bestCmCandidate;
	
	private String proposedCm;
	
	private String bestPm;
	
	private StringBuilder influenceVoter;// = new HashMap<>();
	
	private StringBuilder rebelParty;// = new HashMap<>();
	
	private String bestMedia;
	
	private String freeComments;
	
	private StringBuilder dynQues;
	
	/*private String 
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String
	
	private String*/
	
}
