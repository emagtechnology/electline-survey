package com.el.rest.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.el.rest.dao.ElectPartiesDao;
import com.el.rest.dao.SurveyorImp;
import com.el.rest.dao.UserDao;
import com.el.rest.dto.ElectParties;
import com.el.rest.dto.Surveyor;
import com.el.rest.dto.User;
import com.el.rest.model.BoothOfVidhansabha;
import com.el.rest.model.LokSabhaOfIndia;
import com.el.rest.model.StatesOfIndia;
import com.el.rest.model.VidhanSabhaOfIndia;
import com.el.rest.modelImp.BoothOfVidhansabhaImp;
import com.el.rest.modelImp.LokSabhaOfIndiaImp;
import com.el.rest.modelImp.StatesOfIndiaImp;
import com.el.rest.modelImp.VidhanSabhaOfIndiaImp;
import com.el.rest.service.Assignment;
import com.el.rest.service.AssignmentServices;
import com.el.rest.service.Login;
import com.el.rest.survey.DynamicQuesVidhanSabaha;
import com.el.rest.surveyimp.DynamicQuesVidhanSabahaImp;


@RestController
@RequestMapping(value="/admin")
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AssignmentServices assignSurv;

	@Autowired
	private ElectPartiesDao party;

	@Autowired
	private SurveyorImp surveyorImp;

	@Autowired
	private StatesOfIndiaImp indianStates; 

	@Autowired
	private LokSabhaOfIndiaImp indianLoksabha;

	@Autowired
	private VidhanSabhaOfIndiaImp indianVidhansabha;

	@Autowired
	private DynamicQuesVidhanSabahaImp dynQuesVidhan;

	@Autowired
	private BoothOfVidhansabhaImp vidBooth;

	/**
	 * View, add, active and inactive parties.
	 * @return
	 */
	/***************************************************************/
	/* View All Party*/
	@RequestMapping(value="/all_party", method=RequestMethod.GET)
	public Iterable<ElectParties> getAllParty()
	{
		Iterable<ElectParties> data =  party.findAll();
		return data;
	}


	/* View Active Party*/
	@RequestMapping(value="/all_party/active", method=RequestMethod.GET)
	public Iterable<ElectParties> getActiveParty()
	{
		Iterable<ElectParties> data =  party.findAllByActiveParty(true);
		return data;
	}

	/* View Inactive Party*/
	@RequestMapping(value="/all_party/inactive", method=RequestMethod.GET)
	public Iterable<ElectParties> getInactiveParty()
	{
		Iterable<ElectParties> data =  party.findAllByActiveParty(false);
		return data;
	}

	/* Add new Party and update*/
	@RequestMapping(value="/all_party", consumes = "application/json", method=RequestMethod.POST)
	public String saveParty(@RequestBody ElectParties parties)
	{
		party.save(parties);
		return "{\"Party Add\":\"Successful \",\"status\":2000}";
	}

	/*Active/Inactive party*/
	@PutMapping(value="/all_party/{id}")
	public String updateParties(@PathVariable(name="id") Integer id)
	{
		if(party.existsById(id)) {
			ElectParties parties = party.findById(id).get();
			if(parties.isActiveParty()) {
				parties.setActiveParty(false);
				party.save(parties);
			}
			else {
				parties.setActiveParty(true);
				party.save(parties);
			}
			return "{\"Party Update\":\"Successful \",\"status\":2000}";
		}
		else
			return "{\"Party Update\":\"Failed \",\"status\":4000}";
	}

	/**/
	/***************************************************************/


	/**
	 * View, add, active and inactive surveyor.
	 * @return
	 */
	/***************************************************************/
	/*View All Surveyors*/
	@RequestMapping(value="/surveyor", method=RequestMethod.GET)
	public Iterable<Surveyor> getAllSureyor()
	{
		Iterable<Surveyor> data =  surveyorImp.findAll();	
		return data;
	}

	/*View only active surveyor*/
	@RequestMapping(value="/surveyor/active", method=RequestMethod.GET)
	public Iterable<Surveyor> getActiveSureyor()
	{
		Iterable<Surveyor> data =  surveyorImp.findAllByActiveSurveyor(true);	
		return data;
	}

	/*View only inactive surveyor*/
	@RequestMapping(value="/surveyor/inactive", method=RequestMethod.GET)
	public Iterable<Surveyor> getInactiveSureyor()
	{
		Iterable<Surveyor> data =  surveyorImp.findAllByActiveSurveyor(false);	
		return data;
	}

	/*View Surveyor by there email id*/
	@RequestMapping(value="/surveyor/{email}", method=RequestMethod.GET)
	public Surveyor getAllSureyor(@PathVariable(name="email") String email)
	{
		if(surveyorImp.existsBySurvEmail(email)) {
			Surveyor data =  surveyorImp.findBySurvEmail(email);
			return data;
		}
		else
			return new Surveyor();
	}

	/*Add new Surveyor*/
	@RequestMapping(value="/surveyor", consumes = "application/json", method=RequestMethod.POST)
	public String saveSurveyor(@RequestBody Surveyor surveyor)
	{
		try {
			surveyor.setSurvPass(bCryptPasswordEncoder.encode(surveyor.getSurvPass()));
			surveyorImp.save(surveyor);
			return "{\"Surveyor\":\"New Surveyor added\",\"status\":2000}";
		}
		catch (Exception e) {
			return "{\"Surveyor\":\"Something Went Wrong\",\"status\":4000}";
		}
	}

	/*Active/Inactive Surveyor*/
	@PutMapping(value="/surveyor/{id}")
	public String updateSurveyor(@PathVariable(name="id") Integer id)
	{
		if(surveyorImp.existsById(id)) {
			Surveyor survr = surveyorImp.findById(id).get();
			if(survr.isActiveSurveyor()) {
				survr.setActiveSurveyor(false);
				surveyorImp.save(survr);
			}
			else {
				survr.setActiveSurveyor(true);
				surveyorImp.save(survr);
			}
			return "{\"Surveyor Update\":\"Successful \",\"status\":2000}";
		}
		else
			return "{\"Surveyor Update\":\"Failed \",\"status\":4000}";
	}

	/***************************************************************/

	/**
	 * View all States of India.
	 * @return
	 */
	/***************************************************************/
	/*View All States in India*/
	@RequestMapping(value="/indian-states", method=RequestMethod.GET)
	public Iterable<StatesOfIndia> getAllState()
	{
		Iterable<StatesOfIndia> data =  indianStates.findAll();	
		return data;
	}
	/***************************************************************/


	/**
	 * View, add, active and inactive Loksabha.
	 * @return
	 */
	/***************************************************************/
	/*View All Loksabho in India*/
	@RequestMapping(value="/loksabha", method=RequestMethod.GET)
	public Iterable<LokSabhaOfIndia> getAllSabha()
	{
		Iterable<LokSabhaOfIndia> data =  indianLoksabha.findAll();	
		return data;
	}

	/*View Loksabha according to State*/
	@RequestMapping(value="/loksabha/state/{id}", method=RequestMethod.GET)
	public Iterable<LokSabhaOfIndia> getStateWiseSabha(@PathVariable(name="id") Integer id)
	{
		if(indianStates.existsById(id)) {
			Iterable<LokSabhaOfIndia> data =  indianLoksabha.findAllByState(indianStates.findById(id).get());	
			return data;
		}
		else
			return null;
	}

	/*Add new Loksabha according */
	@RequestMapping(value="/loksabha", consumes = "application/json", method=RequestMethod.POST)
	public String saveLoksabha(@RequestBody LokSabhaOfIndia[] loksabha)
	{
		try {
			for(LokSabhaOfIndia lk : loksabha) {
				if(indianStates.existsById(lk.getState().getStateId())) {
					indianLoksabha.save(lk);

				}
			}
			return "[{\"LokSabha\":\"Saved Successful\",\"status\":2000}]";

		}
		catch (Exception e) {
			return "[{\"LokSabha\":\"Something Went Wrong\",\"status\":4000}]";
		}

	}


	/***************************************************************/

	/**
	 * View, add, active and inactive Vidhansabha.
	 * @return
	 */
	/***************************************************************/
	/*View All Vidhansabha in India*/
	@RequestMapping(value="/vidhansabha", method=RequestMethod.GET)
	public Iterable<VidhanSabhaOfIndia> getAllVidhanSabha()
	{
		Iterable<VidhanSabhaOfIndia> data =  indianVidhansabha.findAll();	
		return data;
	}

	/*View Vidhan sabha according to Loksabha*/
	@RequestMapping(value="/vidhansabha/loksabha/{id}", method=RequestMethod.GET)
	public Iterable<VidhanSabhaOfIndia> getLoksabhaWiseSabha(@PathVariable(name="id") Integer id)
	{
		if(indianLoksabha.existsById(id)) {
			Iterable<VidhanSabhaOfIndia> data =  indianVidhansabha.findAllByLokSabha(indianLoksabha.findById(id).get());	
			return data;
		}
		else
			return null;
	}

	/*Add new vidhan sabha according */
	@RequestMapping(value="/vidhansabha", consumes = "application/json", method=RequestMethod.POST)
	public String saveLoksabha(@RequestBody VidhanSabhaOfIndia[] vidhasabha)
	{
		try {
			for(VidhanSabhaOfIndia vs : vidhasabha) {
				if(indianLoksabha.existsById(vs.getLokSabha().getLokId())) {
					indianVidhansabha.save(vs);

				}
			}
			return "[{\"VidhanSabha\":\"Saved Successful\",\"status\":2000}]";

		}
		catch (Exception e) {
			return "[{\"VidhanSabha\":\"Something Went Wrong\",\"status\":4000}]";
		}

	}


	/***************************************************************/

	/**
	 * View, add, active and inactive Loksabha.
	 * @return
	 */
	/***************************************************************/
	/*View All assignment with surveyor  */
	@RequestMapping(value="/assign_surveyor", method=RequestMethod.GET)
	public Iterable<Assignment> getAllSurveyorAssign()
	{
		Iterable<Assignment> data =  assignSurv.findAll();
		return data;
	}

	/*View assignment by assign id */
	@RequestMapping(value="/assign_surveyor/{id}", method=RequestMethod.GET)
	public Assignment getSurveyorAssign(@PathVariable(name="id") Integer id)
	{
		Assignment data =  assignSurv.findById(id).get();
		return data;
	}

	/*@GetMapping("/testQ")
	public String testQu()
	{

		if(vidBooth.existsByBoothNumberAndVidhanS(1, 
				indianVidhansabha.findById(24).get())){

			return "test ok";	
		}
		else
			return "test fail";
	}*/

	@RequestMapping(value="/assign_surveyor", consumes = "application/json", method=RequestMethod.POST)
	public String saveSurveyorAssign(@RequestBody Assignment assignment)
	{

		try {
			if(surveyorImp.existsById(assignment.getSurveyor().getSurvId())) {

				for (Map.Entry<Integer,String> entry : assignment.getBoothData().entrySet())  {
					/*  System.out.println("Key = " + entry.getKey() + 
			                             ", Value = " + entry.getValue());*/ 
					/*if(vidBooth.existsByBoothNumber(entry.getKey()))*/
					System.out.println("Test1:- " + vidBooth.existsByBoothNumberAndVidhanS(entry.getKey(), 
							indianVidhansabha.findById(assignment.getVidhanSabha().getVidhanSabhaId()).get()));
					if(vidBooth.existsByBoothNumberAndVidhanS(entry.getKey(), 
							indianVidhansabha.findById(assignment.getVidhanSabha().getVidhanSabhaId()).get())){
						System.out.println("test2:- " + vidBooth.findByBoothNumberAndVidhanS(entry.getKey(),
								indianVidhansabha.findById(assignment.getVidhanSabha().getVidhanSabhaId()).get()).getBoothName());
						assignment.getBoothData().put(entry.getKey(), vidBooth.findByBoothNumberAndVidhanS(entry.getKey(),
								indianVidhansabha.findById(assignment.getVidhanSabha().getVidhanSabhaId()).get()).getBoothName());

					}
				}
				assignSurv.save(assignment);
				Surveyor srvr = surveyorImp.findById(assignment.getSurveyor().getSurvId()).get();
				srvr.setAssignID(assignment.getAssignId());
				surveyorImp.save(srvr);
				return "{\"Surveyor Assignment\":\"Assignment Added \",\"status\":2000}";
			}
			else
				return "{\"Surveyor Assignment\":\"Check Surveyor Email \",\"status\":2000}";
		}
		catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			return "{\"Surveyor\":\"Something Went Wrong\",\"status\":4000}";
		}
	}

	@PutMapping(value="/assign_surveyor")
	public String updateSurveyorAssign(@RequestBody Assignment assignment)
	{
		try {
			if(surveyorImp.existsById(assignment.getSurveyor().getSurvId())) {
				for (Map.Entry<Integer,String> entry : assignment.getBoothData().entrySet())  {
					/*  System.out.println("Key = " + entry.getKey() + 
			                             ", Value = " + entry.getValue());*/ 
					/*if(vidBooth.existsByBoothNumber(entry.getKey()))*/ 
					if(vidBooth.existsByBoothNumberAndVidhanS(entry.getKey(), assignment.getVidhanSabha()))
					{
						assignment.getBoothData().put(entry.getKey(), vidBooth.findByBoothNumberAndVidhanS(entry.getKey(),
								indianVidhansabha.findById(assignment.getVidhanSabha().getVidhanSabhaId()).get()).getBoothName());
					}
				}
				assignSurv.save(assignment);
				return "{\"Surveyor Assignment\":\"Assignment updated \",\"status\":2000}";
			}
			else
				return "{\"Surveyor Assignment\":\"Check Surveyor Email \",\"status\":2000}";
		}
		catch (Exception e) {
			e.getStackTrace();
			return "{\"Surveyor\":\"Something Went Wrong\",\"status\":4000}";
		}
	}


	/***************************************************************/

	/**
	 * View, add, active and inactive Admin User.
	 * @return
	 */
	/***************************************************************/
	/*View All admin user  */
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public Iterable<User> getAllAdmin()
	{
		Iterable<User> data =  userDao.findAll();
		return data;
	}

	/*View Admin by email */
	@RequestMapping(value="/user/{email}", method=RequestMethod.GET)
	public User getEmailUser(@PathVariable(name="email") String email)
	{
		User data =  userDao.findByEmail(email);
		return data;
	}

	/* Add new admin with diff. role*/
	@RequestMapping(value="/user", consumes = "application/json", method=RequestMethod.POST)
	public String saveAdmin(@RequestBody User usr)
	{
		try {
			if(!userDao.existsByEmail(usr.getEmail())) {
				usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()));
				userDao.save(usr);
				return "{\"Admin\":\"New Admin Added \",\"status\":2000}";
			}
			else
				return "{\"Admin\":\"Email Exist \",\"status\":2005}";
		}
		catch (Exception e) {
			e.getStackTrace();
			return "{\"Admin\":\"Something Went Wrong\",\"status\":4000}";
		}
	}

	/*Active/Inactive Admin by super admin*/
	@PutMapping(value="/user/{id}")
	public String updateAdmin(@PathVariable(name="id") Integer id)
	{
		if(userDao.existsById(id)) {
			User usr = userDao.findById(id).get();
			if(usr.isEnabled()) {
				usr.setEnabled(false);
				userDao.save(usr);
			}
			else {
				usr.setEnabled(true);
				userDao.save(usr);
			}
			return "{\"Admin Update\":\"Successful \",\"status\":2000}";
		}
		else
			return "{\"Admin Update\":\"Failed \",\"status\":4000}";
	}

	/********************************************************/


	/**
	 * <p style="color:red">For login </p>
	 * 
	 * @param login
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get-login", method=RequestMethod.POST, consumes = "application/json")
	public String getLogin(@RequestBody Login login) {

		if(userDao.existsByEmail(login.getEmail())) {
			User usr = new User();
			usr = userDao.findByEmail(login.getEmail());
			PasswordEncoder pass = bCryptPasswordEncoder;
			if(pass.matches(login.getPassword(), usr.getPassword())) {
				if(usr.isEnabled())
					return "{\"Login\":\"Success\",\"status\":3004}";
				else
				{
					return "{\"Login\":\"You are no longer a verified user.\",\" status\":3008}";
				}
			}
			else
				return "{\"Login\":\"Fail check email or pass\",\"status\":4004}";
		}
		else
			return "{\"Login\":\"Fail check email or pass!\",\"status\":4004}";
	}


	/**
	 * View, add, active and inactive Dynamic Question.
	 * @return
	 */
	/***************************************************************/
	/* View All Dynamic Question*/
	@RequestMapping(value="/dyn_Ques", method=RequestMethod.GET)
	public Iterable<DynamicQuesVidhanSabaha> getAllDynQues()
	{
		Iterable<DynamicQuesVidhanSabaha> data =  dynQuesVidhan.findAll();
		return data;
	}

	/*view Dynamic Question based on vidhansabha id*/
	@RequestMapping(value="/dyn_Ques/{vsId}", method=RequestMethod.GET)
	public Iterable<DynamicQuesVidhanSabaha> getAllDynQuesVs(@PathVariable(name="vsId") int vsId)
	{
		Iterable<DynamicQuesVidhanSabaha> data =  dynQuesVidhan.findAllByVidhanSAndActive(indianVidhansabha.findById(vsId).get(), true);
		return data;
	}

	@RequestMapping(value="/dyn_Ques", consumes = "application/json", method=RequestMethod.POST)
	public String saveDynQues(@RequestBody DynamicQuesVidhanSabaha[] dynQ)
	{
		for(DynamicQuesVidhanSabaha dq: dynQ) {
			dynQuesVidhan.save(dq);
		}
		return "[{\"Ques\":\"New Ques Added \",\"status\":2000}]";

	}

	/**
	 * View, add, active and inactive Booth.
	 * @return
	 */
	/***************************************************************/
	/* View All Booths*/
	@RequestMapping(value="/vidhan_booth", method=RequestMethod.GET)
	public Iterable<BoothOfVidhansabha> getAllBooth()
	{
		Iterable<BoothOfVidhansabha> data =  vidBooth.findAll();
		return data;
	}

	/*view Dynamic Question based on vidhansabha id*/
	@RequestMapping(value="/vidhan_booth/{vsId}", method=RequestMethod.GET)
	public Iterable<BoothOfVidhansabha> getAllBoothVs(@PathVariable(name="vsId") int vsId)
	{
		Iterable<BoothOfVidhansabha> data =  vidBooth.findAllByVidhanSAndActive(indianVidhansabha.findById(vsId).get(), true);
		return data;
	}

	@RequestMapping(value="/vidhan_booth", consumes = "application/json", method=RequestMethod.POST)
	public String saveBoothVs(@RequestBody BoothOfVidhansabha[] boothV)
	{
		for(BoothOfVidhansabha dq: boothV) {
			vidBooth.save(dq);
		}
		return "[{\"Booth\":\"New Booth Added \",\"status\":2000}]";

	}


	/*@DeleteMapping(value="/all_party/{id}")
	public void deleteParty(@PathVariable(name="id") Integer id)
	{
		party.deleteById(id);
	}*/


	/*@RequestMapping(value= {"/manage/survey"}, method = RequestMethod.GET)
    public ModelAndView managePage() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("title", "Manage");
        mav.addObject("atManage", true);
        return mav;
    }

	@RequestMapping(value= {"/","/home", "/index"}, method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("title", "Home");
        mav.addObject("atHome", true);
        return mav;
    }

	@RequestMapping(value="/login")
	public ModelAndView login(@RequestParam(name="error", required= false) String error) {
		ModelAndView mv= new ModelAndView("login");

		if(error != null)
		{
			mv.addObject("message", "Invalid Username or Password");
		}

		mv.addObject("title", "Login");
		//mv.addObject("atLogin", true);

		return mv;
	}

	@RequestMapping(value= "/userAdd", method = RequestMethod.GET)
    public ModelAndView addUsrPage(Model model) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("title", "Add New User");
        mav.addObject("atAddUsr", true);
        model.addAttribute("users", new User());
        return mav;
    }


	@RequestMapping(value="/userAdd", method=RequestMethod.POST)
	public void addData(@ModelAttribute("users") User user, BindingResult results, Model model,
			HttpServletRequest request)
	{
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userDao.save(user);

	}*/
}
