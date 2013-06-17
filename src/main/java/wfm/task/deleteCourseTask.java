package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wfm.bean.User;
import wfm.db.ACT_ID_USER;
import wfm.db.Course;


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

	public void deleteCourse(String taskId, int id) {
		businessProcess.startTask(taskId);
		
		
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
		

		businessProcess.setVariable("approvalAction", "logout");
		businessProcess.completeTask();
	}

}
