package wfm.task;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.cdi.ActivitiCdiException;
import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.runtime.ProcessInstance;

/*
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.xml.DOMConfigurator;
 */
import wfm.bean.User;
import wfm.db.ACT_ID_USER;
import wfm.bean.CourseListBackingBean;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

@Stateful
@Named
@ConversationScoped
public class LoginTask {


	//private static final Logger log = Logger.getLogger(LoginTask.class);
	private static final Logger log = LoggerFactory.getLogger(LoginTask.class);


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


	public ProcessInstance startLogin() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("group", "empty"); // empty entry


		try {
			ACT_ID_USER dbUser = entityManager.find(ACT_ID_USER.class,
					user.getUsername());
			
			if (dbUser != null) {
				if (user.getPassword().equals(dbUser.getPwd_())) {
					logedIn = true;
					
					businessProcess.setVariable("userMail", dbUser.getEmail_());
					businessProcess.setVariable("userName", dbUser.getId_());
					
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
			log.error("Exception parsing login from db: "+e.getMessage());

		}

		if (isLogedIn()) { // check if Trainer or Member
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
			variables.put("loggedIn", logedIn);
			
			

			try {
				return businessProcess.startProcessByKey("sccms", variables);
			} catch (ActivitiCdiException ex) {
				log.error("no process found... "+ex.getMessage());
			}
		}


		return null;
	}

	public void validateUser(FacesContext context, UIComponent component, Object value) {
		ACT_ID_USER dbUser = null;
		try {		
			dbUser = entityManager.find(ACT_ID_USER.class, ((String) value));
		} catch (Exception e) {
			log.error("Exception parsing login from db: "+e.getMessage());

		}
		if (dbUser!=null) {
			user.setUsername(dbUser.getId_());
		}	
		else {user.setUsername(null);}
	}

	public void validatePw(FacesContext context, UIComponent component, Object value){
		ACT_ID_USER dbUser =null;
		try {		
			dbUser = entityManager.find(ACT_ID_USER.class, user.getUsername());

		}	catch (Exception e) {
			log.error("Exception parsing login from db: "+e.getMessage());

		}

		if (dbUser == null || value==null || !dbUser.getPwd_().equals((String) value)) {

			((UIInput)component).setValid(false);
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("WRONG USERNAME OR PASSWORD");
			message.setDetail("Please recheck your credentials");
			context.addMessage(component.getClientId(context), message);
			user.setUsername("");
			//throw new ValidatorException(new FacesMessage("Wrong Username or Password", null));
		}
		else {
			((UIInput)component).setValid(true);
		}	
	}


}
