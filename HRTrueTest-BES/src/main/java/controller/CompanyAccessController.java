package main.java.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.model.CompanyPersistence;
import main.java.model.config.CategorySet;
import main.java.model.config.RequestHeaderNames;
import main.java.model.dao.CategoryData;
import main.java.model.dao.TemplateData;
import main.java.model.dao.TestData;

@RestController
@RequestMapping("authorized/company")
@RolesAllowed("COMPANY")
public class CompanyAccessController {
	@Autowired
	CompanyPersistence model;
	@Autowired
	CategorySet categories;
	
	@RequestMapping(value="/category-list", method=RequestMethod.GET)
	public Iterable<CategoryData> getAllCategories(){
		return CategorySet.convertToCategoryDataTree(categories);
	}
	
	@RequestMapping(value="/templates", method=RequestMethod.GET)
	public Iterable<TemplateData> getTemplates(@RequestHeader(RequestHeaderNames.HEADER_USER_ID) Long userId){	
		return model.getTemplatesForId(userId);
	}
	
	@RequestMapping(value="/add-template", method=RequestMethod.POST)
	public IJsonModel addTemplate(@RequestHeader(RequestHeaderNames.HEADER_USER_ID) Long userId, 
			@RequestBody TemplateData template){
		model.addTemplateForId(userId, template);

		return new SuccessJsonModel("ok");
	}
	@RequestMapping(value="/create-multiple-tests", method=RequestMethod.POST)
	public IJsonModel createTest(@RequestHeader(RequestHeaderNames.HEADER_USER_ID) Long userId,
			@RequestBody List<TestData> tests){
		model.createMultipleTests(userId, tests, categories);

		return new SuccessJsonModel("ok");
	}
}
