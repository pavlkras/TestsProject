package main.java.controller;

import java.util.Random;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.java.model.dao.CompanyData;
import main.java.model.interfaces.IDbGenModel;

public class DbGenApp {
	private static final byte ROLE_COMPANY = 1;
	private static final int SPECIALIZATIONS_OPT = 12;
	private static final int EMPLOYEES_CNT_OPT = 6;
	private static final byte QUESTIONS_AMOUNT_MAX_CNT = 10;
	private static final byte DIFFICULTY_OPT = 3;
	private static final int COMPANIES_CNT = 20;
	private static final int ROOT_CATEGORIES_CNT = 5;
	private static final int CHILD_CAT_CNT = 20;
	private static final int TEMPLATES_MAX_CNT = 10;
	private static final int ITEMS_PER_TEMPLATE_MAX_CNT = 20;
	
	private static Random random = new Random();
	
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");
		IDbGenModel model = (IDbGenModel)ctx.getBean("dbGen");
		
		for (int i = 0; i < ROOT_CATEGORIES_CNT; ++i){
			model.addRootCategory();
		}
		
		for (int i = 0; i < CHILD_CAT_CNT; ++i){
			model.addChildCategory();
		}	
		
		for (int i = 0; i < COMPANIES_CNT; ++i){
			CompanyData data = new CompanyData(getEmail(i), getPassword(i), getCompanyRole(), getRandomString("Comp_Name_"), getRandomString("Site_"), getRandomSpecialization(), getRandomEmployeesAmnt()); 
			model.addCompany(data);
			int templ_cnt = random.nextInt(TEMPLATES_MAX_CNT);
			for (int j = 0; j < templ_cnt; ++j){
				String templateName = "Template_" + i + "_" + j;
				model.addTemplate(templateName, data);
				int items_cnt = random.nextInt(ITEMS_PER_TEMPLATE_MAX_CNT);
				for (int k = 0; k < items_cnt; ++k){
					byte amount = getRandomQuestionsAmount();
					byte difficulty = getRandomDifficulty();
					model.addTemplateItem(amount, difficulty, templateName, data);
				}
			}
		}
		
		ctx.close();
	}

	private static byte getRandomDifficulty() {
		return (byte)random.nextInt(DIFFICULTY_OPT);
	}

	private static byte getRandomQuestionsAmount() {
		return (byte)(random.nextInt(QUESTIONS_AMOUNT_MAX_CNT) + 1);
	}

	private static byte getCompanyRole() {
		return ROLE_COMPANY;
	}

	private static String getPassword(int i) {
		return new String("passwd" + i);
	}

	private static String getEmail(int i) {
		return new String("mail" + i + "@aol.com");
	}

	private static byte getRandomEmployeesAmnt() {
		return (byte)random.nextInt(EMPLOYEES_CNT_OPT);
	}

	private static byte getRandomSpecialization() {
		// TODO Auto-generated method stub
		return (byte)random.nextInt(SPECIALIZATIONS_OPT);
	}

	private static String getRandomString(String prefix) {
		return new String(prefix + random.nextInt(100));
	}
}
