package main.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.TokenJsonModel;
import main.java.model.TestingPersistence;
import main.java.model.dao.BaseQuestionData;
import main.java.model.dao.CandidateData;
import main.java.security.AuthenticationTimeout;
import main.java.security.config.RolesMap;
import main.java.security.dao.User;
import main.java.security.util.JwtUtil;

@RestController
@RequestMapping("testing")
public class TestingProcessController {
	@Autowired
	TestingPersistence model;
	@Autowired
	RolesMap roles;
	
	@RequestMapping(value="/start-testing/{testDesc}", method=RequestMethod.POST)
	public IJsonModel getTokenAndStartTesting(@PathVariable String testDesc){
		CandidateData candidate = null;
		try {
			candidate = model.getCandidateData(testDesc);
		}
		catch (Exception e){
			//TODO handle concrete exception class
		}
		
		User u = new User();
		u.setId(candidate.getId());
		u.setUsername(candidate.getEmail());
		u.setRole(roles.get("ROLE_CANDIDATE").toString());
		
		JwtUtil util = new JwtUtil();
		String token = util.generateToken(u);
		AuthenticationTimeout timeout = AuthenticationTimeout.getInstance();
		timeout.updateTimeout(u.getUsername());
		return new TokenJsonModel(token);
	}
	
	@RequestMapping(value="/{testDesc}", method=RequestMethod.GET)
	Iterable<BaseQuestionData> getTest(@RequestHeader("Authorization") String authorization, 
			@PathVariable String testDesc){
		long id = new JwtUtil().getUserId(authorization);
		
		return model.getTest(id, testDesc);
	}
}
