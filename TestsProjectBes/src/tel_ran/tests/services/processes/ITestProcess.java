package tel_ran.tests.services.processes;

import tel_ran.tests.entitys.Test;

public interface ITestProcess extends Runnable {
	
	void setTest(Test test);

}
