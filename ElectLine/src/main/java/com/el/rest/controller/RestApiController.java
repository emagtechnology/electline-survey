package com.el.rest.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.el.rest.dao.ElectPartiesDao;
import com.el.rest.dao.SurveyorImp;
import com.el.rest.dto.ElectParties;
import com.el.rest.dto.Surveyor;
import com.el.rest.modelImp.VidhanSabhaOfIndiaImp;
import com.el.rest.service.Assignment;
import com.el.rest.service.AssignmentServices;
import com.el.rest.service.Login;
import com.el.rest.service.SurvData;
import com.el.rest.service.WriteCsvToResponse;
import com.el.rest.survey.DynamicQuesAns;
import com.el.rest.survey.SurveeFamily;
import com.el.rest.survey.SurveeRelative;
import com.el.rest.survey.SurveyMain;
import com.el.rest.surveyimp.SurveyMainImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class RestApiController {

	@Autowired
	private SurveyMainImp mainImp;

	@Autowired
	private AssignmentServices assignSurv;

	@Autowired
	private SurveyorImp surveyorImp;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ElectPartiesDao party;

	@Autowired
	private VidhanSabhaOfIndiaImp indianVidhansabha;

	
	  @GetMapping(value="/api-view") public void method(HttpServletResponse
	  httpServletResponse) { httpServletResponse.setHeader("Location",
	  "/swagger-ui.html"); httpServletResponse.setStatus(302); }
	 

	/**
	 * View Parties by surveyor, Only Active party.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all_party", method = RequestMethod.GET)
	public Iterable<ElectParties> getParty() {
		Iterable<ElectParties> data = party.findAllByActiveParty(true);
		return data;
	}

	/* View Surveyor by there email id */
	@RequestMapping(value = "/surveyor/{email}", method = RequestMethod.GET)
	public Surveyor getSureyor(@PathVariable(name = "email") String email) {
		if (surveyorImp.existsBySurvEmail(email)) {
			Surveyor data = surveyorImp.findBySurvEmail(email);
			return data;
		} else
			return null;
	}

	@RequestMapping(value = "/survee_data", method = RequestMethod.GET)
	public Iterable<SurveyMain> getAllVoter() {
		Iterable<SurveyMain> data = mainImp.findAll();

		return data;
	}

	/*
	 * @RequestMapping(value="/survee_data/surveyor/{email}",
	 * method=RequestMethod.GET) public Iterable<SurveyMain>
	 * getVoterBySurveyor(@PathVariable(name="email") String email) {
	 * Iterable<SurveyMain> data =
	 * mainImp.findAllByAssign(assignSurv.findByAssignId(surveyorImp.findBySurvEmail
	 * (email).getAssignID())); return data; }
	 */

	public List<SurvData> rawData(List<SurveyMain> data) {
		List<SurvData> dt = new LinkedList<>();
		for (SurveyMain sm : data) {
			SurvData sd = new SurvData();
			sd.setBoothName(sm.getBoothName().trim());
			sd.setSurveyorName(sm.getAssign().getSurveyor().getSurvName().replace(",", " ").trim());
			sd.setSurveyorEmail(sm.getAssign().getSurveyor().getSurvEmail().trim());
			sd.setSurveeName(sm.getSurveeDetail().getSurveeName().replace(",", " ").trim());
			sd.setSurveeContact(String.valueOf(sm.getSurveeDetail().getSurveeContact()));
			sd.setSurveeAge(String.valueOf(sm.getSurveeDetail().getSurveeAge()));
			sd.setSurveeAddress(sm.getSurveeDetail().getSurveeAddress());
			sd.setSurveeEmployed(String.valueOf(sm.getSurveeDetail().isSurveeEmployed()));
			sd.setSurveeVoterIdAvl(String.valueOf(sm.getSurveeDetail().isSurveeVoterIdAvl()));

			StringBuilder surveeFamily = new StringBuilder();

			for (SurveeFamily sf : sm.getSurveeFamily()) {
				surveeFamily.append(sf.getSurveeFamilyMemberName()).append(": ")
						.append(sf.getSurveeFamilyMemberContact()).append(": ").append(sf.getSurveeFamilyMemberAge())
						.append(": ").append(String.valueOf(sf.isSurveeFamilyMemberEmployed())).append(": ")
						.append(String.valueOf(sf.isSurveeFamilyMemberVoterIdAvl())).append("  /");
			}
			sd.setSurveeFamily(surveeFamily);

			StringBuilder surveeRelative = new StringBuilder();

			for (SurveeRelative sr : sm.getSurveeRelative()) {
				surveeRelative.append(sr.getSurveeRelativeName()).append(": ").append(sr.getSurveeRelativeContact())
						.append(": ").append(sr.getSurveeRelativeAddress()).append(" /");
			}
			sd.setSurveeRelative(surveeRelative);

			sd.setPartyLeading(sm.getSurveyQues().getPartyLeading());

			sd.setBestCandidateArea(sm.getSurveyQues().getBestCandidateArea());

			String cstEq = "";
			for (Map.Entry<String, Integer> entry : sm.getSurveyQues().getCastEquation().entrySet()) {
				cstEq += entry.getKey() + ": " + entry.getValue() + " /";
			}
			sd.setCastEquation(cstEq);

			sd.setTotalVoter(sm.getSurveyQues().getTotalVoter());

			String isu = "";
			for (String is : sm.getSurveyQues().getIssues()) {
				isu += is + " / ";
			}
			sd.setIssues(isu);

			String sgsn = "";
			for (String sgn : sm.getSurveyQues().getSuggestions()) {
				sgsn += sgn + " / ";
			}
			sd.setSuggestions(sgsn);

			sd.setPartyWish(sm.getSurveyQues().getPartyWish());

			StringBuilder partyWiseCandidate = new StringBuilder();
			for (Map.Entry<String, String> entry : sm.getSurveyQues().getPartyWiseCandidate().entrySet()) {
				partyWiseCandidate.append(entry.getKey() + ": " + entry.getValue().replace(",", ";")).append(" / ");
			}
			sd.setPartyWiseCandidate(partyWiseCandidate);

			sd.setBestCmCandidate(sm.getSurveyQues().getBestCmCandidate());

			sd.setProposedCm(sm.getSurveyQues().getProposedCm());

			sd.setBestPm(sm.getSurveyQues().getBestPm());

			StringBuilder influenceVoter = new StringBuilder();
			for (Map.Entry<String, Long> entry : sm.getSurveyQues().getInfluenceVoter().entrySet()) {
				influenceVoter.append(entry.getKey() + ": " + entry.getValue()).append(" / ");
			}
			sd.setInfluenceVoter(influenceVoter);

			StringBuilder rebelParty = new StringBuilder();
			for (Map.Entry<String, String> entry : sm.getSurveyQues().getRebelParty().entrySet()) {
				rebelParty.append(entry.getKey() + ": " + entry.getValue()).append(" / ");
			}
			sd.setRebelParty(rebelParty);

			sd.setBestMedia(sm.getSurveyQues().getBestMedia());

			String frcmnt = "";
			for (String sgfcm : sm.getSurveyQues().getFreeComments()) {
				frcmnt += sgfcm + " / ";
			}
			sd.setFreeComments(frcmnt);

			StringBuilder dynQues = new StringBuilder();

			for (DynamicQuesAns dq : sm.getDqVidhan()) {
				dynQues.append(dq.getDQues().getDyQuesn()).append("? : ").append(dq.getDAns()).append("  _/ ");
			}
			sd.setDynQues(dynQues);

			dt.add(sd);
		}

		return dt;
	}

	@RequestMapping(value = "/survee_data/surveyor/{email}/data.csv", method = RequestMethod.GET, produces = "TEXT/CSV")
	public void getVoterBySurveyor(@PathVariable(name = "email") String email, HttpServletResponse response)
			throws IOException {
		List<SurveyMain> data = mainImp
				.findAllByAssign(assignSurv.findByAssignId(surveyorImp.findBySurvEmail(email).getAssignID()));
		// return data;
		WriteCsvToResponse.writeSurvey(response.getWriter(), rawData(data));
	}

	@RequestMapping(value = "/survee_data/vidhansabha/{id}/data-{name}.csv", method = RequestMethod.GET, produces = "TEXT/CSV")
	public void getVoterByVidhansabha(@PathVariable(name = "id") Integer id, HttpServletResponse response)
			throws IOException {
		List<SurveyMain> smData = new LinkedList<>();
		for (Assignment asid : assignSurv.findAllByVidhanSabha(indianVidhansabha.findById(id).get())) {
			smData.addAll(mainImp.findAllByAssign(assignSurv.findByAssignId(asid.getAssignId())));
		}

		// data = smData;
		WriteCsvToResponse.writeSurvey(response.getWriter(), rawData(smData));
		// return data;
	}

	@RequestMapping(value = "/survee_data", consumes = "application/json", method = RequestMethod.POST)
	public String saveSurvee(@RequestBody SurveyMain surveyMain) {
		if (surveyMain.getBoothName() == null) {
			return "{\"Data\":\"Sorry, Data can not submit, Please contact to admin.\",\"status\":3001}";
		} else {
			mainImp.save(surveyMain);
			return "{\"Data\":\"Success\",\"status\":2004}";
		}
	}

	/* View assignment by assign id */
	@RequestMapping(value = "/assign_surveyor/{id}", method = RequestMethod.GET)
	public Assignment getSurveyorAssign(@PathVariable(name = "id") Integer id) {
		Assignment data = assignSurv.findById(id).orElse(null);
		return data;
	}

	@RequestMapping(value = "/get-login", method = RequestMethod.POST, consumes = "application/json")
	public String getLogin(@RequestBody Login login) throws Exception {

		if (surveyorImp.existsBySurvEmail(login.getEmail())) {
			Surveyor srv = new Surveyor();
			srv = surveyorImp.findBySurvEmail(login.getEmail());
			PasswordEncoder pass = bCryptPasswordEncoder;
			if (pass.matches(login.getPassword(), srv.getSurvPass())) {
				if (srv.isActiveSurveyor()) {
					/**/
					Assignment dt = getSurveyorAssign(srv.getAssignID());
					if (dt == null) {
						return "{\"Login\":\"Success, Please contact to admin because you don't have any survey assignment\",\"status\":3001}";
					} else {
						if (dt.getBoothData().isEmpty() || dt.getGramPanchayatName().isEmpty()) {
							return "{\"Login\":\"Success, Please contact to admin because you can not conduct survey.\",\"status\":3001}";
						} else {
							ObjectMapper mapper = new ObjectMapper();
							return "{\"Login\":\"Success\",\"assign\":" + mapper.writeValueAsString(dt)
									+ ",\"status\":2004}";
						}
					}
				} else
					return "{\"Login\":\"Failed, Please contact to admin\",\"status\":3004}";
			} else
				return "{\"Login\":\"Fail check email or pass\",\"status\":4004}";
		} else
			return "{\"Login\":\"Fail check email or pass!\",\"status\":4004}";
	}

}
