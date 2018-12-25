package com.el.rest.survey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.el.rest.model.VidhanSabhaOfIndia;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
//@Embeddable
public class DynamicQuesVidhanSabaha implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int DynQuesId;
	
	@OneToOne
	private VidhanSabhaOfIndia vidhanS;
	
	//@ElementCollection
	private String dyQuesn;// = new HashSet<>();
	
	/*@ManyToMany(mappedBy="dqVidhan", fetch = FetchType.LAZY)
	private Set<SurveyMain> srvMain = new HashSet<>();*/
	
	private boolean active = true;
}
