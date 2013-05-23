package wfm.task;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.runtime.ProcessInstance;

import wfm.bean.User;

@Stateful
@Named
@ConversationScoped
public class LoginTask {

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private User user;

	public void checkLogin() {
		if (user.getUsername().equals("kermit"))
			System.out.println("kermit logged in");
	}

	public ProcessInstance startLogin() {
		System.out.println("login task called");

	    Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put("username", user.getUsername());
	    return businessProcess.startProcessByKey("bookclass", variables);
	}
}
