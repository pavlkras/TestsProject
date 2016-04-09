package main.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.JsonKeys;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.model.dao.LoginData;
import main.java.model.dao.UserData;
import main.java.model.interfaces.IUserModel;

@RestController
@RequestMapping("guest")
public class GuestAccessController {
	@Autowired
	IUserModel model;
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public IJsonModel registerCompany(@RequestBody UserData user){
		model.registerUser(user);
		return new SuccessJsonModel(JsonKeys.R_OK);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public IJsonModel loginToSystem(@RequestBody LoginData login){
		return new SuccessJsonModel(JsonKeys.R_OK);
	}
}
