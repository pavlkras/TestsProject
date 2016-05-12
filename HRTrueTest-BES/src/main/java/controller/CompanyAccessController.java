package main.java.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.ErrorJsonModel;
import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.model.CompanyPersistence;
import main.java.model.config.CategorySet;
import main.java.model.dao.CategoryData;
import main.java.model.dao.TemplateData;
import main.java.model.dao.TestData;
import main.java.security.JwtAuthenticationFilter;

@RestController
@RequestMapping("authorized/company")
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
	public Iterable<TemplateData> getTemplates(@RequestHeader(JwtAuthenticationFilter.HEADER_USER_ID) String userId){
		long id = Long.parseLong(userId);
		
		return model.getTemplatesForId(id);
	}
	
	@RequestMapping(value="/add-template", method=RequestMethod.POST)
	public IJsonModel addTemplate(@RequestHeader(JwtAuthenticationFilter.HEADER_USER_ID) String userId, 
			@RequestBody TemplateData template){
		long id = Long.parseLong(userId);
		try{
			model.addTemplateForId(id, template);
		} catch (PersistenceException e){
			return new ErrorJsonModel("persistence error");
		}
		return new SuccessJsonModel("ok");
	}
	@RequestMapping(value="/create-multiple-tests", method=RequestMethod.POST)
	public IJsonModel createTest(@RequestHeader(JwtAuthenticationFilter.HEADER_USER_ID) String userId,
			@RequestBody List<TestData> tests){
		long id = Long.parseLong(userId);
		try {
			model.createMultipleTests(id, tests, categories);
		} catch (NoResultException e){
			return new ErrorJsonModel(e.getMessage());
		}
		catch (PersistenceException e){
			return new ErrorJsonModel("persistence error");
		}
		return new SuccessJsonModel("ok");
	}
}
