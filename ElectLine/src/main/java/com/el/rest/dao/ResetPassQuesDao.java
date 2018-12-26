package com.el.rest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.ResetPassQues;

@Repository
public interface ResetPassQuesDao extends CrudRepository<ResetPassQues, Integer> {

}
