package com.el.rest.modelImp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.model.LokSabhaOfIndia;
import com.el.rest.model.VidhanSabhaOfIndia;

@Repository
public interface VidhanSabhaOfIndiaImp extends CrudRepository<VidhanSabhaOfIndia, Integer> {

	List<VidhanSabhaOfIndia> findAllByLokSabha(LokSabhaOfIndia lokSabhaOfIndia);
}
