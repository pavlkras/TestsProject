package tel_ran.tests.aop;

import java.util.List;

import tel_ran.tests.services.utils.FileManagerService;

public class dfdf {
	public static void main(String[] args){
		FileManagerService tt = new FileManagerService();
		//// test creating file location  tree 
		System.out.println(tt.initializeTestFileStructure(12345L,5421L));
		//// test save image person
		//tt.saveImage(12345L,54321L, "fffffffffffffffffffffffffffffggg");
		tt.saveScreen(12345L,54321L, "xxxxxxfdgdffgdfgdfgdfgdffggg");
		//// test save json file 
		tt.saveJson(12345L,54321L,"xxxxxxfdgdffgdfgdfgdfgdffggg");
		//// test delete folders
		//System.out.println(tt.deleteTest(12345L,54321L));
		System.out.println(tt.deleteCompany(12345L));
		//// test for get images list 
		List<String> res = tt.getImage(12345L,54321L);
		System.out.println(res);
		//// test for get screen pictures list 
		List<String> res2 = tt.getScreen(12345L,54321L);
		System.out.println(res2);
		
	}
}
