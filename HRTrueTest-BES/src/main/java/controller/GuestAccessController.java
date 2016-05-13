package main.java.controller;


import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.ErrorJsonModel;
import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.jsonsupport.TokenJsonModel;
import main.java.model.GuestPersistence;
import main.java.model.config.ActivityTypeMap;
import main.java.model.config.EmployeesAmountMap;
import main.java.model.dao.ActivityTypeData;
import main.java.model.dao.CompanyData;
import main.java.model.dao.CredentialsData;
import main.java.model.dao.EmployeesAmountData;
import main.java.model.dao.LoginData;
import main.java.security.JwtUserFactory;
import main.java.security.dao.JwtUser;
import main.java.security.util.JwtUtil;
import main.java.utils.Crypto;

@RestController
@RequestMapping("guest")
public class GuestAccessController {
	@Autowired
	GuestPersistence model;
	@Autowired
	ActivityTypeMap activityTypeOpts;
	@Autowired
	EmployeesAmountMap employeesAmountOpts;
	
	@RequestMapping(value="/signup/company", method=RequestMethod.POST)
	public IJsonModel registerCompany(@RequestBody CompanyData company){
		try{
			model.registerCompany(company);
		} catch (JpaSystemException e){
			return new ErrorJsonModel(e.getMessage());
		} catch (PersistenceException e) {
			return new ErrorJsonModel("invalid email/password");
		}
		return new SuccessJsonModel("ok");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public IJsonModel loginToSystem(@RequestBody LoginData login){
		CredentialsData data = null;
		try {
			data = model.getCompanyData(login.getEmail());
		}
		catch (JpaSystemException | NoResultException e){
			return new ErrorJsonModel("login doesn't exist");
		}
		if (Crypto.matches(login.getPassword(), data.getPassword())){
			JwtUser u = JwtUserFactory.create(data);
			
			JwtUtil util = new JwtUtil();
			String token = util.generateToken(u);
			
			return new TokenJsonModel(token);
		}
		return new ErrorJsonModel("invalid credentials");
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
