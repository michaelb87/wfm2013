package wfm.task;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.runtime.ProcessInstance;

import wfm.bean.User;
import wfm.db.ACT_ID_USER;

@Stateful
@Named
@ConversationScoped
public class LoginTask {

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private User user;
	
	private String logedIn="false";

	@PersistenceContext
	private EntityManager entityManager;

	public void checkLogin() {
		if (user.getUsername().equals("kermit"))
			System.out.println("kermit logged in");
	}

	public ProcessInstance startLogin() {
		System.out.println("login task called");
		
		try{
			ACT_ID_USER dbUser = entityManager.find(ACT_ID_USER.class, user.getUsername());
			
			if((dbUser != null) && (user.getPassword().equals(dbUser.getPwd_()))) //TODO fix
			{
				logedIn="true";
				System.out.println("log debug: " + dbUser.toString());
			}
			else if(dbUser == null) logedIn="false";
		}
		catch(Exception e){
			System.out.println("uhoh..bad");
			e.printStackTrace();			
		}
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		
		variables.put("loggedIn", logedIn);
		return businessProcess.startProcessByKey("sccms", variables);
	}
}
