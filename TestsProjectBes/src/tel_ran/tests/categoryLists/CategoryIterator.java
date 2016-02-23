package tel_ran.tests.categoryLists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tel_ran.tests.entitys.Category;
import tel_ran.tests.entitys.CategoryGenerated;

public class CategoryIterator implements Iterator<Category> {

	List<CategoryGenerated> categories;
	int counter = 0;
	int size;
	
	public CategoryIterator (List<Category> list) {
		this.categories = new ArrayList<>();
		for(Category cat : list) {
			if(cat instanceof CategoryGenerated && ((CategoryGenerated) cat).isFinal()) {
				this.categories.add((CategoryGenerated) cat);
			}			
		}	
		this.size = this.categories.size();
	}
	
	@Override
	public boolean hasNext() {		
		return counter < size;
	}

	@Override
	public Category next() {
		Category result = categories.get(counter++);
		return result;
	}
	
	public int getSize() {
		return size;
	}
	
	

}
