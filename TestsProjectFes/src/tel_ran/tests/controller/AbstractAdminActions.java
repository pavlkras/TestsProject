package tel_ran.tests.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.strings.IMessages;

public abstract class AbstractAdminActions {	
	public static final String RESULT = "result"; 	
	ICommonAdminService adminService;
	//// AJAX for upload picture for new question on creating
	@RequestMapping(value="/upload-file", method=RequestMethod.POST)
	public @ResponseBody JsonResponse uploadFile(HttpServletRequest request) {	
		JsonResponse res = new JsonResponse(); 
		String concatRes = " ";

		// TO DO Method !!!!
		res.setStatus("SUCCESS");
		res.setResult(concatRes);
		return res;
	}


	// ----------------------- METHODS FOR LISTS AND TEXTS----------------------------------------------- // 


	protected String buildingUsersCategoryBoxTestHTML() {
		String result;
		List<String> categoryList;
		try {
			categoryList = adminService.getUsersCategories1FromDataBase("");
			result = getOptionsFromList(categoryList, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));			
		} catch (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);		
			System.out.println("no data base found");//----------------------------------------------------sysout
		}

		return result;// return too page after action

	}

	protected String checkUsersCategoryBoxTestHTML(StringBuffer baseStr) {
		String result = null;
		List<String> categoryList = adminService.getUsersCategories1FromDataBase("");
		List<String> results = new ArrayList<>();
		for (String str : categoryList) {
			if(baseStr.indexOf(str)==-1)
				results.add(str);				
		}
		if(results.size() > 0) {
			result = getOptionsFromList(results, null);
		}
		return result;
	}

	protected String buildingCategory2CheckBoxTextHTML() {		
		String result;
		List<String> categoryList;
		try {
			categoryList = adminService.getAllCategories2FromDataBase();
			result = getOptionsFromList(categoryList, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));			
		} catch (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);		
			System.out.println("no data base found");//----------------------------------------------------sysout
		}

		return result;// return too page after action
	}

	protected String buildingCategory1CheckBoxTextHTML() {		
		String result;
		List<String> categoryList;
		try {
			categoryList = adminService.getAllCategories1FromDataBase();
			result = getOptionsFromList(categoryList, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));			
		} catch (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);		
			System.out.println("no data base found");//----------------------------------------------------sysout
		}

		return result;// return too page after action
	}

	protected String buildingMetaCategory1CheckBoxTextHTML() {		
		String result;
		List<String> categoryList;
		try {
			categoryList = adminService.getAllMetaCategoriesFromDataBase("");
			for(int i = 0; i < ICommonData.USER_CATEGORY.length; i++) {
				categoryList.add(ICommonData.USER_CATEGORY[i]);
			}
			result = getOptionsFromList(categoryList, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));			
		} catch (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);		
			System.out.println("no data base found");//----------------------------------------------------sysout
		}

		return result;// return too page after action
	}
	private String getStringInBold(String str) {
		return "<b>" + str + "</b>";
	}
	private String getOptionsFromList(List<String> lst, String message) {
		StringBuilder result = new StringBuilder();
		if (lst != null) {			
			for (String tresR : lst) {	
				if(tresR!=null)
					result.append("<option value='" + tresR + "'> "+ tresR + "</option>");					
			}				
		} else {
			result.append(message);
		}
		return result.toString();
	}

	// ---------------------- INNER CLASS ------------------------------------------------- //

	protected class JsonResponse {
		private String status = null;
		private Object result = null;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Object getResult() {
			return result;
		}
		public void setResult(Object result) {
			this.result = result;
		}
	}

	// ------------------------- UPDATE PAGE -------------------------------------------------- //





}
