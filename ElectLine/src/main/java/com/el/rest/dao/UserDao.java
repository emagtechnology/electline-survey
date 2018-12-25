package com.el.rest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.el.rest.dto.User;

@Repository("userDAO")
public interface UserDao extends CrudRepository<User, Integer> {
	User findByEmail(String email);
	boolean existsByEmail(String email);
}