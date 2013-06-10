package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;

import wfm.bean.User;
import wfm.db.Course;


@Stateful
@Named
@ConversationScoped
public class deleteCourseTask {

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
		
		
		System.out.println("User: " + user.getUsername());
		
		// delete from database:
				
		try{
				System.out.println("Searching course with Id: "+id);
				course = entityManager.find(Course.class, id);
				
				System.out.println("Course found: " + course.toString());
								
			    entityManager.remove(course);
			    entityManager.flush();		  
			    
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

		businessProcess.setVariable("approvalAction", "logout");
		businessProcess.completeTask();
	}

}
