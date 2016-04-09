package main.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.model.interfaces.IUserModel;

@RestController
@RequestMapping("authorized")
public class AuthorizedAccessController {
	@Autowired
	IUserModel model;
	
}
