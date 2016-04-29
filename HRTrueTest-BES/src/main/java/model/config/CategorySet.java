package main.java.model.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

import main.java.entities.CategoryConfigEntity;
import main.java.model.dao.CategoryData;

@Component
public class CategorySet extends LinkedHashSet<CategoryConfigEntity> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public static final String ABSTRACT_TASK = "Abstract Task";
	public static final String ATTENTION_TASK = "Attention Task";
	public static final String NUMERICAL_TASK = "Numerical Task";
	public static final String AMERICAN_TEST = "American Test";
	public static final String OPEN_QUESTION = "Open Question";
	public static final String PROGRAMMING_TASK = "Programming Task";
	public static final String JAVA_PROGRAMMING_TASK = "Java";
	
	private static Map<String, Integer> nameToIdMap = new LinkedHashMap<>();
	private static Map<Integer, String> idToNameMap = new LinkedHashMap<>();
	
	public CategorySet(Collection<? extends CategoryConfigEntity> c) {
		super(c);
		for (CategoryConfigEntity category : this){
			nameToIdMap.put(category.getName(), category.getId());
			idToNameMap.put(category.getId(), category.getName());
		}
	}
	
	public static CategoryData convertToCategoryData(CategorySet categories, CategoryConfigEntity entity){
		CategoryData ret = null;
		
		for (CategoryConfigEntity category : categories){
			if (category == entity){
				int id = category.getId();
				String name = category.getName();
				List<CategoryData> children = null;
				for (CategoryConfigEntity child : categories){
					if (child.getParent() == entity){
						if (children == null)
							children = new ArrayList<>();
						children.add(convertToCategoryData(categories, child));
					}
				}
				ret = new CategoryData(id, name, children);
				break;
			}
		}
		
		return ret;
	}
	
	public static Iterable<CategoryData> convertToCategoryDataTree(CategorySet categories){
		List<CategoryData> topLevelCategories = new ArrayList<>();
		for (CategoryConfigEntity category : categories){
			if (category.getParent() == null)
				topLevelCategories.add(convertToCategoryData(categories, category));
		}
		return topLevelCategories;
	}
	
	public static int getCategoryIdByName(String name){
		Integer ret = nameToIdMap.get(name);
		
		if (ret == null)
			throw new IllegalArgumentException("category name <" + name + "> not found");
		
		return ret;
	}
	
	public static String getCategoryNameById(int id){
		String ret = idToNameMap.get(id);
		
		if (ret == null)
			throw new IllegalArgumentException("category id <" + id + "> not found");
		
		return ret;
	}
}
