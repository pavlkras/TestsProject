package main.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.java.model.dao.CategoryData;
import main.java.model.interfaces.IUserModel;

@RestController
@RequestMapping("authorized/company")
public class CompanyAccessController {
	@Autowired
	IUserModel model;
	
	@RequestMapping(value="/category-list", method=RequestMethod.GET)
	public Iterable<CategoryData> getChildCategoriesForParent(@RequestHeader("Authorization") String authorization,
			@RequestParam(required=false, name="parent") String str_parent_id){
		Integer parent_id = null;
		if (str_parent_id != null && !str_parent_id.isEmpty()){
			parent_id = Integer.parseInt(str_parent_id);
		}
		return model.getChildCategories(parent_id);
	}
}
