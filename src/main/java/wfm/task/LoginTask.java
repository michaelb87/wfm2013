package wfm.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.cdi.ActivitiCdiException;
import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

import wfm.bean.User;
import wfm.db.ACT_ID_USER;
import wfm.bean.CourseListBackingBean;

@Stateful
@Named
@ConversationScoped
public class LoginTask {
	private static final Logger log = Logger.getLogger(LoginTask.class.getName());

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private User user;
	
	@Inject
	private CourseListBackingBean courses;

	public boolean logedIn = false;

	public boolean isLogedIn() {
		return logedIn;
	}

	@PersistenceContext
	private EntityManager entityManager;

	/*public void checkLogin() {
		if (user.getUsername().equals("kermit"))
			System.out.println("kermit logged in");
	}*/

	public ProcessInstance startLogin() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("group", "empty"); // empty entry

		System.out.println("login task called");
		log.debug("logger works");
		log.info("Test");

		try {
			ACT_ID_USER dbUser = entityManager.find(ACT_ID_USER.class,
					user.getUsername());

			if (dbUser != null) {
				if (user.getPassword().equals(dbUser.getPwd_())) {
					logedIn = true;
				} else {
					user.setPassword("");
					logedIn = false;
				}
			} else {
				user.setPassword("");
				user.setUsername("");
				logedIn = false;
			}
		} catch (Exception e) {
			System.out.println("Exception parsing login from db");
			// e.printStackTrace();
		}

		if (isLogedIn()) { // check for membership type
			variables.put("username", user.getUsername());

			Query q = entityManager
					.createNativeQuery("SELECT group_ID_  FROM ACT_ID_MEMBERSHIP a WHERE a.USER_ID_ ='"
							+ user.getUsername() + "'");

			@SuppressWarnings("unchecked")
			List<String> userGroups = q.getResultList();
			if (userGroups.contains("Trainer")) {
				variables.put("group", "Trainer");
			} else if (userGroups.contains("Member")) {
				variables.put("group", "Member");
				
			}

		}

		variables.put("loggedIn", logedIn);
		

		
		try {
			return businessProcess.startProcessByKey("sccms", variables);
		} catch (ActivitiCdiException ex) {
			System.out.println("no process found... this is very dirty!");
		}
		
		
		
		return null;
	}
}
