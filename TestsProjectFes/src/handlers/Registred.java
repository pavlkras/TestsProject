package handlers;

import java.util.List;

import tel_ran.tests.services.interfaces.ICommonAdminService;

public abstract class Registred extends AbstractHandler {

	protected ICommonAdminService commonService;

	public void setCommonService(ICommonAdminService commonService) {
		this.commonService = commonService;
	}

	@Override
	public boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions) {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(nQuestions);
		return ((ICommonAdminService)commonService).moduleForBuildingQuestions(token, metaCategory, category2, levelOfDifficulty , num);	
	}
	
	@Override
	public List<String> getPossibleMetaCaterories() {
		List<String> result = commonService.getPossibleMetaCaterories();
		return result;
	}
	

}
