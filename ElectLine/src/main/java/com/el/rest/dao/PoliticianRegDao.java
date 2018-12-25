package com.el.rest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.PoliticianReg;

@Repository
public interface PoliticianRegDao extends CrudRepository<PoliticianReg, Integer> {

	PoliticianReg findByPoltyEmail(String email);
	PoliticianReg findByPoltyIdAndPoltyPass(int id, String pass);
	boolean existsByPoltyEmail(String email);
	boolean existsByPoltyEmailAndIsPaid(String email,boolean paid);
}
