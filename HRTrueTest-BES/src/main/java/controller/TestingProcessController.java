package main.java.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.ErrorJsonModel;
import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.TokenJsonModel;
import main.java.model.TestingPersistence;
import main.java.model.config.NamesAndFormats;
import main.java.model.dao.BaseQuestionData;
import main.java.model.dao.LoginData;
import main.java.security.dao.JwtUser;
import main.java.security.util.JwtUtil;
import main.java.utils.Crypto;
import main.java.utils.UserLoaderService;

@RestController
@RequestMapping("test/{testDesc}")
public class TestingProcessController {
	@Autowired
	TestingPersistence model;
	@Autowired
	UserLoaderService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	@RolesAllowed("CANDIDATE")
	public Iterable<BaseQuestionData> getTest(@RequestHeader(NamesAndFormats.HEADER_USER_ID) Long userId, 
			@PathVariable String testDesc){
		return model.getTest(userId, testDesc);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public IJsonModel loginToSystem(@PathVariable String testDesc,
			@RequestBody LoginData login){
		JwtUser user = service.loadUserByUsername(login.getEmail());
		
		long testCandidateId = model.getCandidateFromTest(testDesc);
		
		if (user.getId() != testCandidateId){
			return new ErrorJsonModel("candidate isn't authorized for this test");
		}
		
		String token = null;
		if (Crypto.matches(login.getPassword(), user.getPassword())){
			token = new JwtUtil().generateToken(user);
		} else {
			throw new BadCredentialsException("bad credentials");
		}
		
		return new TokenJsonModel(token);
	}
}
