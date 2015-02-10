package tel_ran.tests.controller;

import java.util.List;

class CompanyTestsResutlsHandler {

	public final static String delimiter = "/--/";

	
	//parser of bes response for common tests results
	static String compileToViewTestCommon(List<String> bes_response) {
		String defaultColumnName = "Column"; // the default name for columns in the output table. Will apply if bes_response contains one string only
		String rowAr [] = bes_response.get(0).split(delimiter);
		int columnCount = rowAr.length;
		StringBuffer output = new StringBuffer();
		if(bes_response.size() == 1){
			output.append("<tr>");
			for(int i = 1; i < columnCount+1; i++){
				output.append("<th>");
				output.append(defaultColumnName);
				output.append(" ");
				output.append(i);
				output.append("</th>");
			}
			output.append("</tr><tr>");
			for(int i = 0; i < columnCount; i++){
				output.append("<td>");
				output.append(rowAr[i]);
				output.append("</td>");
			}
			output.append("</tr>");	
		}else if(bes_response.size() > 1){
			output.append("<tr>");
			for(int i = 0; i < columnCount; i++){
				output.append("<th>");
				output.append(rowAr[i]);
				output.append("</th>");
			}
			output.append("</tr>");
			for(int i = 1; i < bes_response.size(); i++){
				rowAr = bes_response.get(i).split(delimiter);
				if(rowAr.length == columnCount){
						output.append("<tr>");
					for(int j = 0; j < columnCount; j++){
						output.append("<td>");
						output.append(rowAr[j]);
						output.append("</td>");
					}
					output.append("</tr>");
				}
			}
		}		
		return output.toString();
	}

	
	
	//parser of bes response for individual test result	
	static String compileToViewTestDetails(String bes_response) {
		String rowAr [] = bes_response.split(delimiter);
		
		return null;
	}
}
