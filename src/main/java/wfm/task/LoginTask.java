package wfm.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.cdi.ActivitiCdiException;
import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.ProcessInstance;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;
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
	
	private static final String ACCESS_TOKEN = "1507264177-0ftwhAZ15w0WH90mGchgYdPRD2EEyEJLRKfHtm8";
	private static final String ACCESS_TOKEN_SECRET = "SKENHIkD28MfI3gaXEWDOaCtG9TTYFUmT4fz7VPPo";
	private static final String CONSUMER_KEY = "EFC6rs5cgA8MV37KjTisqg";
	private static final String CONSUMER_SECRET = "hx9eWPpklMNLj8RfMPZtd5Vusj3sK7SSu4Ah9yRuQ";

	
	//private static final Logger log = Logger.getLogger(LoginTask.class);
	//private static final Log log = LogFactory.getLog(LoginTask.class);

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

		log.info("login task called");

		//URL url = Loader.getResource("log4j.properties");
		//PropertyConfigurator.configure(url);

		log.debug("Test debug log4j");
		log.info("Test info log4j isdebug: "+log.isDebugEnabled());
		log.debug("Test debug log4j");
		log.error("error test");
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

		}

		variables.put("loggedIn", logedIn);

		//test if posting on Twitter works:
		testTwitter();

		try {
			return businessProcess.startProcessByKey("sccms", variables);
		} catch (ActivitiCdiException ex) {
			log.error("no process found... "+ex.getMessage());
		}



		return null;
	}


	private void testTwitter() {
			
			ConfigurationBuilder builder = new ConfigurationBuilder();
	    	builder.setOAuthAccessToken(ACCESS_TOKEN);
	    	builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
	    	builder.setOAuthConsumerKey(CONSUMER_KEY);
	    	builder.setOAuthConsumerSecret(CONSUMER_SECRET);
	        OAuthAuthorization auth = new OAuthAuthorization(builder.build());
	        Twitter twitter = new TwitterFactory().getInstance(auth);
			try {
				twitter.updateStatus("User "+user.getUsername()+" logged in.");
			} catch (TwitterException e) {
				System.err.println("Error occurred while updating the status: "+e.getMessage());
				return;
			}
	        System.out.println("Successfully updated the status.");
		
		
	}
}
