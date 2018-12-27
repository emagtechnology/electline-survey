package com.el.rest.surveyimp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.el.rest.service.Assignment;
import com.el.rest.survey.SurveyMain;

public interface SurveyMainImp extends CrudRepository<SurveyMain, Integer> {
	
	List<SurveyMain> findAllByAssign(Assignment assign);
	List<SurveyMain> findTop5ByAssign(Assignment assign);
}
