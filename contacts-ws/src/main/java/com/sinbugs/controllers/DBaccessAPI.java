package com.sinbugs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DBaccessAPI {
	
	@RequestMapping(value="/dbaccess", method=RequestMethod.GET)
    public String getGroup() {
		try {
			
			return "Data Group";
			
		} catch (Exception e) {
			return "Error: "+e.getMessage();
		}
	}

}
