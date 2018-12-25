package com.el.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.el.rest.dao.PoliticianRegDao;
import com.el.rest.dto.PoliticianReg;
import com.el.rest.service.Login;

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
	@GetMapping(value="/poltyRegData")
	public Iterable<PoliticianReg> getPoltyData()
	{
		Iterable<PoliticianReg> data = poltyDao.findAll();
		
		return data;
	}
	
	
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
	
	@RequestMapping(value="/get-login", method=RequestMethod.POST, consumes = "application/json")
	public String getLogin(@RequestBody Login login) {

		if(poltyDao.existsByPoltyEmail(login.getEmail())) {
			PoliticianReg polReg = new PoliticianReg();
			polReg = poltyDao.findByPoltyEmail(login.getEmail());
			PasswordEncoder pass = bCryptPasswordEncoder;
			if(pass.matches(login.getPassword()+""+login.getEmail(), polReg.getPoltyPass())) {
				if(polReg.isActivePolty())
					return "{\"Login\":\"Success\",\"status\":3004}";
				else
				{
					
						return "{\"Login\":\"You are not a verified user.\",\"status\":400}";
					
				}
			}
			else
				return "{\"Login\":\"Fail check email or pass\",\"status\":4004}";
		}
		else
			return "{\"Login\":\"Fail check email or pass\",\"status\":4004}";
	}
}