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

	private String courseName;

	public void deleteCourse(String taskId, int id) {

	//	businessProcess.startTask(taskId);

		// delete from database:

		try{
			log.info("Searching course with Id: "+id);
			course = entityManager.find(Course.class, id);

			log.info("Course found: " + course.toString());

			courseName = course.getName();

			//TODO:
/*
			//removing the course from users
			for (ACT_ID_USER u : course.getUsers()) {
				u.getCourses().remove(course);
			}
			//removing all users from the collection of users
			course.getUsers().clear();*/

			entityManager.remove(course);

			entityManager.flush();		  

			log.info("Course deleted successfully...");

		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		
		businessProcess.completeTask();
		
		businessProcess.setVariable("deletedCourseName", courseName);	
		
		//variables for messages
		businessProcess.setVariable("courseAction", "deleted");
		businessProcess.setVariable("courseFromAction", courseName);
	}

	public void cancel(String taskId) {			    
	//	businessProcess.startTask(taskId);
		businessProcess.setVariable("routeAction", "cancel");
		businessProcess.completeTask();
		businessProcess.setVariable("courseAction", "cancelled");
		
	}


}
