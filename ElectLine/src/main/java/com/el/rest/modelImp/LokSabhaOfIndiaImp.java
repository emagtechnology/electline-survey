package com.el.rest.modelImp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.model.LokSabhaOfIndia;
import com.el.rest.model.StatesOfIndia;

@Repository
public interface LokSabhaOfIndiaImp extends CrudRepository<LokSabhaOfIndia, Integer> {
	List<LokSabhaOfIndia> findAllByState(StatesOfIndia statesOfIndia);
}
