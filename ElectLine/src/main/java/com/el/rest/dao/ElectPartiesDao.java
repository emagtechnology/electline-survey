package com.el.rest.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.ElectParties;

@Repository
public interface ElectPartiesDao extends CrudRepository<ElectParties, Integer> {

	List<ElectParties> findAllByActiveParty(boolean party);
}
