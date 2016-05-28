package main.java.controller;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.java.entities.CredentialsEntity;
import main.java.model.DbGenPersistence;
import main.java.model.config.AuthorityName;
import main.java.model.dao.CompanyData;
import main.java.model.dao.TemplateItemData;

public class DbGenApp {
	private static final byte QUESTIONS_AMOUNT_MAX_CNT = 10;
	private static final byte DIFFICULTY_OPT = 3;
	private static final int COMPANIES_CNT = 20;
	private static final int TEMPLATES_MAX_CNT = 10;
	private static final int ITEMS_PER_TEMPLATE_MAX_CNT = 20;
	
	private static Random random = new Random();
	private static DbGenPersistence model = null;
	
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");
		model = (DbGenPersistence)ctx.getBean("dbGen");
		
		for (int i = 0; i < COMPANIES_CNT; ++i){
			CompanyData data = new CompanyData();
			data.setEmail(getEmail(i));
			data.setPassword(getPassword(i));
			data.setName(getRandomString("Comp_Name_"));
			data.setSite(getRandomString("Site_"));
			data.setActivityType(getRandomByte(model.getActivityTypes().size()));
			data.setEmployeesAmnt(getRandomByte(model.getEmployeesAmounts().size()));
			data.setAuthorities(CredentialsEntity.convertDbMaskToAuthorities(AuthorityName.ROLE_COMPANY.code()));
			model.addCompany(data);
			int templ_cnt = random.nextInt(TEMPLATES_MAX_CNT);
			for (int j = 0; j < templ_cnt; ++j){
				String templateName = "Template_" + i + "_" + j;
				model.addTemplate(templateName, data);
				addTemplateItems(templateName, data);
			}
		}
		
		ctx.close();
	}

	private static void addTemplateItems(String templateName, CompanyData data) {
		Set<TemplateItemData> items = new LinkedHashSet<>();
		int items_cnt = random.nextInt(ITEMS_PER_TEMPLATE_MAX_CNT);
		for (int k = 0; k < items_cnt; ++k){
			byte amount = getRandomQuestionsAmount();
			byte difficulty = getRandomDifficulty();
			byte category = getRandomByte(model.getCategories().size());
			TemplateItemData tid = new TemplateItemData(0, difficulty, amount, category);
			
			boolean existsInSet = false;
			for (TemplateItemData item : items){
				if (item.equals(tid)){
					item.setAmount((byte)(item.getAmount() + tid.getAmount()));
					existsInSet = true;
				}
			}
			if (!existsInSet)
				items.add(tid);
		}
		for (TemplateItemData item : items){
			model.addTemplateItem(item, templateName, data);
		}
	}

	private static byte getRandomByte(int maxValue) {
		return (byte) random.nextInt(maxValue);
	}

	private static byte getRandomDifficulty() {
		return (byte)random.nextInt(DIFFICULTY_OPT);
	}

	private static byte getRandomQuestionsAmount() {
		return (byte)(random.nextInt(QUESTIONS_AMOUNT_MAX_CNT) + 1);
	}

	private static String getPassword(int i) {
		return new String("passwd" + i);
	}

	private static String getEmail(int i) {
		return new String("mail" + i + "@aol.com");
	}

	private static String getRandomString(String prefix) {
		return new String(prefix + random.nextInt(100));
	}
}
