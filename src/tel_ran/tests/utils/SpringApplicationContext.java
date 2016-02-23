package tel_ran.tests.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
		
public class SpringApplicationContext implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		CONTEXT = context;
	}

	/*
	 * Returns the instance of the 
	 */
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}
