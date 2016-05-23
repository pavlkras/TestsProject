package main.java.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.jsonsupport.TokenJsonModel;
import main.java.model.GuestPersistence;
import main.java.model.config.ActivityTypeMap;
import main.java.model.config.EmployeesAmountMap;
import main.java.model.dao.ActivityTypeData;
import main.java.model.dao.CompanyData;
import main.java.model.dao.EmployeesAmountData;
import main.java.model.dao.LoginData;
import main.java.security.dao.JwtUser;
import main.java.security.util.JwtUtil;
import main.java.utils.Crypto;
import main.java.utils.UserLoaderService;

@RestController
@RequestMapping("guest")
public class GuestAccessController {
	@Autowired
	GuestPersistence model;
	@Autowired
	UserLoaderService service;
	@Autowired
	ActivityTypeMap activityTypeOpts;
	@Autowired
	EmployeesAmountMap employeesAmountOpts;
	
	@RequestMapping(value="/signup/company", method=RequestMethod.POST)
	public CompanyData registerCompany(@RequestBody CompanyData company){
		return model.registerCompany(company);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public IJsonModel loginToSystem(@RequestBody LoginData login){
		JwtUser user = service.loadUserByUsername(login.getEmail());
		
		String token = null;
		if (Crypto.matches(login.getPassword(), user.getPassword())){
			token = new JwtUtil().generateToken(user);
		} else {
			throw new BadCredentialsException("bad credentials");
		}
		
		return new TokenJsonModel(token);
	}
	
	@RequestMapping(value="/activity-types", method=RequestMethod.GET)
	public Iterable<ActivityTypeData> getActivityTypes(){
		return ActivityTypeMap.convertToActivityTypeDataList(activityTypeOpts);
	}
	
	@RequestMapping(value="/employees-amounts", method=RequestMethod.GET)
	public Iterable<EmployeesAmountData> getEmployeesAmountOpts(){
		return EmployeesAmountMap.convertToEmployeesAmountDataList(employeesAmountOpts);
	}
}
