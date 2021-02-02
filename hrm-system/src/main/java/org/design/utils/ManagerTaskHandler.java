package org.design.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.design.model.Employee;
import org.design.service.EmployeeService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ManagerTaskHandler implements TaskListener, ApplicationContextAware {

	private static final long serialVersionUID = 1L;
	private static ApplicationContext applicationContext = null;

	/**
	 * 注入Spring容器
	 * @param arg0
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		if (applicationContext == null) {
			applicationContext = arg0;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setAppCtx(ApplicationContext webAppCtx) {
		if (webAppCtx != null) {
			applicationContext = webAppCtx;
		}
	}

	public static final Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		EmployeeService employeeService = (EmployeeService) getBean("employeeService");
		Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
		Employee manager = employeeService.findManager(employee.getManagerId());
		//·赋值代理人的名称
		delegateTask.setAssignee(manager.getUsername());
	}
}
