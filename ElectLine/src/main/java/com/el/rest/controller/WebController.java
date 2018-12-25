package com.el.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

	@RequestMapping(value= {"/"}, method = RequestMethod.GET)
    public ModelAndView soonPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("title", "Comming Soon");
     //   FileUtil.uploadFile(request);
        return mav;
    }
	
	
}
