package main.java.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.jsonsupport.IJsonModel;
import main.java.jsonsupport.SuccessJsonModel;
import main.java.model.CompanyPersistence;
import main.java.model.config.CategorySet;
import main.java.model.config.NamesAndFormats;
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
	public Iterable<TemplateData> getTemplates(@RequestHeader(NamesAndFormats.HEADER_USER_ID) Long userId){	
		return model.getTemplatesForId(userId);
	}
	
	@RequestMapping(value="/add-template", method=RequestMethod.POST)
	public IJsonModel addTemplate(@RequestHeader(NamesAndFormats.HEADER_USER_ID) Long userId, 
			@RequestBody TemplateData template){
		model.addTemplateForId(userId, template);

		return new SuccessJsonModel("ok");
	}
	@RequestMapping(value="/create-multiple-tests", method=RequestMethod.POST)
	public IJsonModel createTest(@RequestHeader(NamesAndFormats.HEADER_USER_ID) Long userId,
			@RequestBody List<TestData> tests){
		model.createMultipleTests(userId, tests, categories);

		return new SuccessJsonModel("ok");
	}
	@RequestMapping(value="/tests-by-template/{templateId}", method=RequestMethod.GET)
	public Iterable<TestData> getTestsByTemplate(@RequestHeader(NamesAndFormats.HEADER_USER_ID) Long userId,
			@PathVariable Long templateId,
			@RequestParam(name="from", required=false) @DateTimeFormat(pattern=NamesAndFormats.DATE_FORMAT) Date fromDate,
			@RequestParam(name="to",   required=false) @DateTimeFormat(pattern=NamesAndFormats.DATE_FORMAT) Date toDate){
		return model.getTestsByTemplateId(userId, templateId, fromDate, toDate);
	}
}
