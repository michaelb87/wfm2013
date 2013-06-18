package wfm.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.ProcessVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wfm.bean.User;
import wfm.db.ACT_ID_USER;
import wfm.db.Course;
import javax.persistence.Query;

@Stateful
@Named
@ConversationScoped
public class deleteCourseTask {
	
	private static final Logger log = LoggerFactory.getLogger(LoginTask.class);

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;
	
	@Inject
	private User user;

	@PersistenceContext
	private EntityManager entityManager;
	
	@ProcessVariable("mails") 
	private Set<String> userMails = new HashSet<String>();

	public void deleteCourse(String taskId, int id) {
		businessProcess.startTask(taskId);
		
		//get all mails from subscribed users
		//h2 ex.: SELECT EMAIL_  FROM USER_COURSE uc, ACT_ID_USER u WHERE uc.USER_ID = u.ID_ AND COURSE_NR = 68 
		Query q = entityManager
				.createNativeQuery("SELECT EMAIL_ FROM USER_COURSE uc, ACT_ID_USER u WHERE uc.USER_ID = u.ID_ AND uc.COURSE_NR ='"+id+"'");
		
		@SuppressWarnings("unchecked")
		List<String> users = q.getResultList();
		//add them to processVariable hashset
		for(String mail: users){
			System.out.println("Email of subscribed person: "+mail);
			userMails.add(mail);
		}
		
		
		log.info("User: " + user.getUsername());
		
		// delete from database:
				
		try{
				log.info("Searching course with Id: "+id);
				course = entityManager.find(Course.class, id);
				
				log.info("Course found: " + course.toString());
				
				//removing the course from users
				for (ACT_ID_USER u : course.getUsers()) {
					u.getCourses().remove(course);
				}
				//removing all users from the collection of users
				course.getUsers().clear();
				
			    entityManager.remove(course);
			   
			    entityManager.flush();		  
			    
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		

		//businessProcess.setVariable("approvalAction", "logout");
		businessProcess.setVariable("deleteProcess", userMails);
		businessProcess.completeTask();
	}

}
