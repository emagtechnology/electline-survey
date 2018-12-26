package com.el.rest.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.PoliticianReg;
import com.el.rest.dto.ResetPassQues;

@Repository
public interface PoliticianRegDao extends CrudRepository<PoliticianReg, Integer> {

	PoliticianReg findByPoltyEmail(String email);
	PoliticianReg findByPoltyIdAndPoltyPass(int id, String pass);
	boolean existsByPoltyEmail(String email);
	boolean existsByPoltyEmailAndIsPaid(String email,boolean paid);
	boolean existsByPoltyEmailAndResetQuesAndPoltyDoB(String email, ResetPassQues passQ, Date dob);
}
