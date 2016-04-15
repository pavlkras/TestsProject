package main.java.controller;

import java.util.Map;

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
import main.java.model.config.ActivityTypeMap;
import main.java.model.config.EmployeesAmountMap;
import main.java.model.dao.CompanyData;
import main.java.model.dao.LoginData;
import main.java.model.interfaces.IUserModel;
import main.java.security.AuthenticationTimeout;
import main.java.security.dao.User;
import main.java.security.util.JwtUtil;
import main.java.utils.Crypto;

@RestController
@RequestMapping("guest")
public class GuestAccessController {
	@Autowired
	IUserModel model;
	@Autowired
	ActivityTypeMap activityTypeOpts;
	@Autowired
	EmployeesAmountMap employeesAmountOpts;
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
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
		CompanyData company = null;
		try {
			company = model.getCompanyData(login.getEmail());
		}
		catch (JpaSystemException | NoResultException e){
			return new ErrorJsonModel("login doesn't exist");
		}
		if (Crypto.matches(login.getPassword(), company.getPassword())){
			User u = new User();
			u.setId(company.getId());
			u.setUsername(company.getName());
			u.setRole(new String("" + company.getRole()));
			
			JwtUtil util = new JwtUtil();
			
			String token = util.generateToken(u);
			AuthenticationTimeout timeout = AuthenticationTimeout.getInstance();
			timeout.updateTimeout(u.getUsername());
			
			return new TokenJsonModel(token);
		}
		return new ErrorJsonModel("invalid credentials");
	}
	
	@RequestMapping(value="/activity-types", method=RequestMethod.GET)
	public Map<Integer, String> getActivityTypes(){
		return activityTypeOpts;
	}
	
	@RequestMapping(value="/employees-amounts", method=RequestMethod.GET)
	public Map<Integer, String> getEmployeesAmountOpts(){
		return employeesAmountOpts;
	}
}
