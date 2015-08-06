package tel_ran.tests.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.controller.Maintenance.JsonResponse;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.interfaces.ICommonAdminService;

public abstract class AbstractAdminActions {	
	
	public static StringBuffer AutoGeneratedHTMLFormText;
	public static  StringBuffer AutoGeneratedInformationTextHTML;
	
	ICommonAdminService adminService;
	
	public AbstractAdminActions(){
		AutoGeneratedHTMLFormText = new StringBuffer();
		AutoGeneratedInformationTextHTML = new StringBuffer();
	}
		
	public String moduleForBuildingQuestions(ICommonAdminService adminService, String category, String nQuestions, String levelOfDifficulty, Model model, String path) {		
		int nQuest = Integer.parseInt(nQuestions);
		boolean actionRes = false;
		try {
			int differentLevel = Integer.parseInt(levelOfDifficulty);
			actionRes = adminService.ModuleForBuildingQuestions(category, null, differentLevel , nQuest);
		} catch (Exception e) {
			System.out.println("catch call maintenanceaction from FES moduleForBuildingQuestions");//----------------------------------------------------sysout
			//e.printStackTrace();
		}
		
		//TEXT:
		// your request to build ... questions made
		// your request to build ... questions failed
		StringBuilder result = new StringBuilder("<br><p class='informationTextP'>");
		result.append(IPublicStrings.YOUR_REQUEST).append(" |").append(nQuestions).
			append("| ").append(IPublicStrings.QUESTION_ITEMS).append(" ");
		if(actionRes){				
			result.append(IPublicStrings.MADE); 		
		}else{			
			result.append(IPublicStrings.FAILED);
		}
		result.append("</p>");
		AutoInformationTextHTML(result.toString());		
		return path;// return too page after action
	}

	protected void AutoInformationTextHTML(String string) {
		AutoGeneratedInformationTextHTML.append(string);
	}
		
	protected String maintenanceOtherResurses(ICommonAdminService adminService, String path) {
		clearStringBuffer();
		AutoGeneratedHTMLFormText.append(buildAutoMetaCategoryList(adminService));
		return path;
	}	
	
	private String buildAutoMetaCategoryList(ICommonAdminService adminService) {
		StringBuilder listResult = new StringBuilder();		
		List<String> categoryList = adminService.GetPossibleMetaCaterories();
		// TEXT OF MESSAGE
		// auto generation isn't available
		return getOptionsFromList(categoryList, IPublicStrings.NO_AUTO_CATEGORIES);
	}
	
	private String getOptionsFromList(List<String> lst, String message) {
		StringBuilder result = new StringBuilder();
		if (lst != null) {			
			for (String tresR : lst) {					
				result.append("<option value='" + tresR + "'> "+ tresR + "</option>");					
			}				
		} else {
			result.append(message);
		}
		return result.toString();
	}
	
	protected String BuildingCategoryCheckBoxTextHTML(ICommonAdminService adminService) {		
		StringBuffer checkedFlyButtons = new StringBuffer();
		try {
			List<String> categoryList = adminService.getAllCategories2FromDataBase();
			if (categoryList != null) {			
				for (String tresR : categoryList) {					
					checkedFlyButtons.append("<option value='" + tresR + "'> "+ tresR + "</option>");					
				}				
			} else {
				//TEXT
				// No Categories in Data Base
				checkedFlyButtons.append("<b>").append(IPublicStrings.NO_CATEGORIES_IN_DB).append("</b>");
			}
		} catch (Exception e) {
			// TEXT
			// No Data Base
			checkedFlyButtons.append("<b>").append(IPublicStrings.NO_DATA_BASE).append("</b>");
			System.out.println("no data base found");//----------------------------------------------------sysout
		}
		return checkedFlyButtons.toString();// return too page after action
	}
	
	private void clearStringBuffer() {
		if(AutoGeneratedHTMLFormText!=null)
			AutoGeneratedHTMLFormText.delete(0, AutoGeneratedHTMLFormText.length());// clear stringbuffer		
	}
	
	public static String AutoGeneratedInformationTextHTML(){	
		String outTextResult = "";
		if(AutoGeneratedInformationTextHTML != null){
			outTextResult = AutoGeneratedInformationTextHTML.toString();	
			AutoGeneratedInformationTextHTML.delete(0, AutoGeneratedInformationTextHTML.length());// clear stringbuffer
		}	
		System.out.println(outTextResult);
		return outTextResult;		
	}
	
	 
	public @ResponseBody JsonResponse HandlerCode(ICommonAdminService adminService, HttpServletRequest request) {			
		JsonResponse res = new JsonResponse(); 
		String concatRes = " ";
		List<String> result = adminService.GetPossibleMetaCaterories();
		for(String re:result){		
			concatRes += "<option value='" + re + "'>"+ re +"</option>";
		}
		res.setStatus("SUCCESS"); 
		res.setResult(concatRes);
		return res;
	}
	
	class JsonResponse {
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
}
