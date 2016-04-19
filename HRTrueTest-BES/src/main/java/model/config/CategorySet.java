package main.java.model.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

import main.java.entities.CategoryConfigEntity;
import main.java.model.dao.CategoryData;

@Component
public class CategorySet extends LinkedHashSet<CategoryConfigEntity> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public CategorySet(Collection<? extends CategoryConfigEntity> c) {
		super(c);
		// TODO Auto-generated constructor stub
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
}
