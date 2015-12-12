package tel_ran.tests.services;

import tel_ran.tests.dao.CategoryMaps;

public class AutoCategoriesService extends AbstractService {

	/**
	 * Return list of possible auto categories.
	 * This list is saved in the Memory by CategoryMaps
	 * @return
	 */
	@Override
	public String getAllElements() {
		return CategoryMaps.getJsonAutoCategories();
	}

	@Override
	public String createNewElement(String dataJson) {
		throw new RuntimeException();
	}
	

}
