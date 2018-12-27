package com.el.rest.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.el.rest.dao.PoliticianRegDao;
import com.el.rest.dao.ResetPassQuesDao;
import com.el.rest.dto.PoliticianReg;
import com.el.rest.modelImp.VidhanSabhaOfIndiaImp;
import com.el.rest.service.Assignment;
import com.el.rest.service.AssignmentServices;
import com.el.rest.service.Login;
import com.el.rest.service.ResetPass;
import com.el.rest.service.SurvData;
import com.el.rest.survey.SurveyMain;
import com.el.rest.surveyimp.SurveyMainImp;

@RestController
@RequestMapping(value="/politician")
public class PoltyController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PoliticianRegDao poltyDao;
	
	@Autowired
	private ResetPassQuesDao resPsQ;
	
	@Autowired
	private SurveyMainImp mainImp;

	@Autowired
	private AssignmentServices assignSurv;

	/*
	 * @Autowired private SurveyorImp surveyorImp;
	 * 
	 * @Autowired private ElectPartiesDao party;
	 */

	@Autowired
	private VidhanSabhaOfIndiaImp indianVidhansabha;

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
	 * get Politician-Registration based email id data from DB. 
	 */	
	@GetMapping(value="/poltyRegData/email-{email}")
	public PoliticianReg getPoltyDataByEmail(@PathVariable(name="email") String email)
	{
		PoliticianReg data = poltyDao.findByPoltyEmail(email);
		
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
			poltyReg.setResetAnswer(bCryptPasswordEncoder.encode(poltyReg.getResetAnswer()));

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
	
	@RequestMapping(value="/reset-pass", method=RequestMethod.POST, consumes = "application/json")
	public String getLogin(@RequestBody ResetPass poltyReg) {

		if(poltyDao.existsByPoltyEmail(poltyReg.getEmail())) {
			PoliticianReg polReg = new PoliticianReg();
			polReg = poltyDao.findByPoltyEmail(poltyReg.getEmail());
			PasswordEncoder answ = bCryptPasswordEncoder;
			if(poltyDao.existsByPoltyEmailAndResetQuesAndPoltyDoB(poltyReg.getEmail(), resPsQ.findById(poltyReg.getResetPassId()).get(), poltyReg.getPoltyDoB())) {
				if(answ.matches(poltyReg.getAnswer(), polReg.getResetAnswer()))
				{
					polReg.setResetAnswer(bCryptPasswordEncoder.encode(polReg.getResetAnswer()));
					poltyDao.save(polReg);
					return "{\"Reset Pass\":\"Success\",\"status\":3004}";
				}
				else
					return "{\"Reset Pass\":\"Fail check security Question\",\"status\":4004}";
			}
			else
				return "{\"Reset Pass\":\"Fail check email or Dob\",\"status\":4004}";
		}
		else
			return "{\"Reset Pass\":\"Fail check email\",\"status\":4004}";
	}
	
	
	
	
	@GetMapping(value = "/survee_data/vidhansabha/{id}")
	public List<SurvData> getAllVoter(@PathVariable(name = "id") Integer id) {
		List<SurveyMain> smData = new LinkedList<>();
		for (Assignment asid : assignSurv.findAllByVidhanSabha(indianVidhansabha.findById(id).get())) {
			smData.addAll(mainImp.findTop5ByAssign(assignSurv.findByAssignId(asid.getAssignId())));
		}

		return new RestApiController().rawData(smData);
	}

	/*
	 * private List<SurvData> rawData(List<SurveyMain> data) { List<SurvData> dt =
	 * new LinkedList<>(); for (SurveyMain sm : data) { SurvData sd = new
	 * SurvData(); sd.setBoothName(sm.getBoothName().trim());
	 * sd.setSurveyorName(sm.getAssign().getSurveyor().getSurvName().replace(",",
	 * " ").trim());
	 * sd.setSurveyorEmail(sm.getAssign().getSurveyor().getSurvEmail().trim());
	 * sd.setSurveeName(sm.getSurveeDetail().getSurveeName().replace(",",
	 * " ").trim());
	 * sd.setSurveeContact(String.valueOf(sm.getSurveeDetail().getSurveeContact()));
	 * sd.setSurveeAge(String.valueOf(sm.getSurveeDetail().getSurveeAge()));
	 * sd.setSurveeAddress(sm.getSurveeDetail().getSurveeAddress());
	 * sd.setSurveeEmployed(String.valueOf(sm.getSurveeDetail().isSurveeEmployed()))
	 * ;
	 * sd.setSurveeVoterIdAvl(String.valueOf(sm.getSurveeDetail().isSurveeVoterIdAvl
	 * ()));
	 * 
	 * StringBuilder surveeFamily = new StringBuilder();
	 * 
	 * for (SurveeFamily sf : sm.getSurveeFamily()) {
	 * surveeFamily.append(sf.getSurveeFamilyMemberName()).append(": ")
	 * .append(sf.getSurveeFamilyMemberContact()).append(": ").append(sf.
	 * getSurveeFamilyMemberAge())
	 * .append(": ").append(String.valueOf(sf.isSurveeFamilyMemberEmployed())).
	 * append(": ")
	 * .append(String.valueOf(sf.isSurveeFamilyMemberVoterIdAvl())).append("  /"); }
	 * sd.setSurveeFamily(surveeFamily);
	 * 
	 * StringBuilder surveeRelative = new StringBuilder();
	 * 
	 * for (SurveeRelative sr : sm.getSurveeRelative()) {
	 * surveeRelative.append(sr.getSurveeRelativeName()).append(": ").append(sr.
	 * getSurveeRelativeContact())
	 * .append(": ").append(sr.getSurveeRelativeAddress()).append(" /"); }
	 * sd.setSurveeRelative(surveeRelative);
	 * 
	 * sd.setPartyLeading(sm.getSurveyQues().getPartyLeading());
	 * 
	 * sd.setBestCandidateArea(sm.getSurveyQues().getBestCandidateArea());
	 * 
	 * String cstEq = ""; for (Map.Entry<String, Integer> entry :
	 * sm.getSurveyQues().getCastEquation().entrySet()) { cstEq += entry.getKey() +
	 * ": " + entry.getValue() + " /"; } sd.setCastEquation(cstEq);
	 * 
	 * sd.setTotalVoter(sm.getSurveyQues().getTotalVoter());
	 * 
	 * String isu = ""; for (String is : sm.getSurveyQues().getIssues()) { isu += is
	 * + " / "; } sd.setIssues(isu);
	 * 
	 * String sgsn = ""; for (String sgn : sm.getSurveyQues().getSuggestions()) {
	 * sgsn += sgn + " / "; } sd.setSuggestions(sgsn);
	 * 
	 * sd.setPartyWish(sm.getSurveyQues().getPartyWish());
	 * 
	 * StringBuilder partyWiseCandidate = new StringBuilder(); for
	 * (Map.Entry<String, String> entry :
	 * sm.getSurveyQues().getPartyWiseCandidate().entrySet()) {
	 * partyWiseCandidate.append(entry.getKey() + ": " +
	 * entry.getValue().replace(",", ";")).append(" / "); }
	 * sd.setPartyWiseCandidate(partyWiseCandidate);
	 * 
	 * sd.setBestCmCandidate(sm.getSurveyQues().getBestCmCandidate());
	 * 
	 * sd.setProposedCm(sm.getSurveyQues().getProposedCm());
	 * 
	 * sd.setBestPm(sm.getSurveyQues().getBestPm());
	 * 
	 * StringBuilder influenceVoter = new StringBuilder(); for (Map.Entry<String,
	 * Long> entry : sm.getSurveyQues().getInfluenceVoter().entrySet()) {
	 * influenceVoter.append(entry.getKey() + ": " +
	 * entry.getValue()).append(" / "); } sd.setInfluenceVoter(influenceVoter);
	 * 
	 * StringBuilder rebelParty = new StringBuilder(); for (Map.Entry<String,
	 * String> entry : sm.getSurveyQues().getRebelParty().entrySet()) {
	 * rebelParty.append(entry.getKey() + ": " + entry.getValue()).append(" / "); }
	 * sd.setRebelParty(rebelParty);
	 * 
	 * sd.setBestMedia(sm.getSurveyQues().getBestMedia());
	 * 
	 * String frcmnt = ""; for (String sgfcm : sm.getSurveyQues().getFreeComments())
	 * { frcmnt += sgfcm + " / "; } sd.setFreeComments(frcmnt);
	 * 
	 * StringBuilder dynQues = new StringBuilder();
	 * 
	 * for (DynamicQuesAns dq : sm.getDqVidhan()) {
	 * dynQues.append(dq.getDQues().getDyQuesn()).append("? : ").append(dq.getDAns()
	 * ).append("  _/ "); } sd.setDynQues(dynQues);
	 * 
	 * dt.add(sd); }
	 * 
	 * return dt; }
	 */
	

		
	
	
}