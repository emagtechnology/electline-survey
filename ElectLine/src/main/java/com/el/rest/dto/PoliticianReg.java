package com.el.rest.dto;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import com.el.rest.model.VidhanSabhaOfIndia;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class PoliticianReg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int poltyId;

	@Column(length=35)
	private String poltyFirstName="";
	
	@Column(length=40)
	private String poltyLastName="";
	
	@Column(length=100,unique=true)
	@Email
	private String poltyEmail="";
	
	//@Column(length=25)
	private String poltyPass="";
	
	private long poltyContact=0;
	
	@Lob
	private byte[] poltyImg;
	
	private boolean isActivePolty = false;
	
    @ManyToOne
   // @JoinColumn(name = "elect_parties_id", nullable = false)
	private ElectParties electParty;
    
    @ManyToOne
    private VidhanSabhaOfIndia vidhanSabha;
    
    @OneToOne
    private ResetPassQues resetQues;
    
    private String resetAnswer;
    
    @JsonIgnore
	private boolean isPaid = false;
	
    @JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date userExpiry = Date.from(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(5).atZone(ZoneId.systemDefault()).toInstant());

    @JsonIgnore
	private String transId =""; 
	
}
