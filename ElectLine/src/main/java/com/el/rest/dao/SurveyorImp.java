package com.el.rest.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.Surveyor;

@Repository
public interface SurveyorImp extends CrudRepository<Surveyor, Integer> {

	Surveyor findBySurvEmail(String email);
	Surveyor findBySurvIdAndSurvPass(int id, String pass);
	boolean existsBySurvEmail(String email);
	
	List<Surveyor> findAllByActiveSurveyor(boolean active);
	
	
}
