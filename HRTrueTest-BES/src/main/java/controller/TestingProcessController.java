package main.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.model.TestingPersistence;
import main.java.model.config.RequestHeaderNames;
import main.java.model.dao.BaseQuestionData;

@RestController
@RequestMapping("testing")
public class TestingProcessController {
	@Autowired
	TestingPersistence model;
	
	/*@RequestMapping(value="/start-testing/{testDesc}", method=RequestMethod.POST)
	public IJsonModel getTokenAndStartTesting(@PathVariable String testDesc){
		CandidateData candidate = null;
		try {
			candidate = model.getCandidateData(testDesc);
		}
		catch (Exception e){
			//TODO handle concrete exception class
		}
		
		JwtUser u = new JwtUser();
		u.setId(candidate.getId());
		u.setUsername(candidate.getEmail());
		u.setRole(roles.get("ROLE_CANDIDATE").toString());
		
		JwtUtil util = new JwtUtil();
		String token = util.generateToken(u);
		AuthenticationTimeout timeout = AuthenticationTimeout.getInstance();
		timeout.updateTimeout(u.getUsername());
		return new TokenJsonModel(token);
	}*/
	
	@RequestMapping(value="/{testDesc}", method=RequestMethod.GET)
	Iterable<BaseQuestionData> getTest(@RequestHeader(RequestHeaderNames.HEADER_USER_ID) String userId, 
			@PathVariable String testDesc){
		long id = Long.parseLong(userId);
		
		return model.getTest(id, testDesc);
	}
}
