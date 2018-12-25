package com.el.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.el.rest.dao.PoliticianRegDao;
import com.el.rest.dto.PoliticianReg;

@RestController
@RequestMapping(value="/politician")
public class PoltyController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PoliticianRegDao poltyDao;
	

	/*
	 * get all Politician-Registration data from DB. 
	 */	
	
	
	/*
	 * Store Politician-Registration data in DB. 
	 */	
	@RequestMapping(value="/poltyRegData", consumes = "application/json", method=RequestMethod.POST)
	public String addData(@RequestBody PoliticianReg poltyReg)
	{
		if(poltyDao.existsByPoltyEmail(poltyReg.getPoltyEmail())){
			return "{\"UserExist\":\"User Already Exist\",\"status\":4000}";
		}
		else {
			PoliticianReg polReg = new PoliticianReg();
			poltyReg.setPoltyPass(bCryptPasswordEncoder.encode(poltyReg.getPoltyPass()+""+poltyReg.getPoltyEmail()));

			poltyDao.findByPoltyEmail(poltyReg.getPoltyEmail());
			polReg = poltyDao.findByPoltyEmail(poltyDao.save(poltyReg).getPoltyEmail());
			
			return "{\"success\":\"Data save successfully\",\"status\":2000}";
		}
	}
}