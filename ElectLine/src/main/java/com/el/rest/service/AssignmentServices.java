package com.el.rest.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.el.rest.dto.Surveyor;
import com.el.rest.model.VidhanSabhaOfIndia;

@Service
public interface AssignmentServices extends CrudRepository<Assignment, Integer>{

	boolean existsBySurveyor(Surveyor srvr);
	
	Assignment findByAssignId(int id);
	List<Assignment> findAllByVidhanSabha(VidhanSabhaOfIndia vsi);
}
