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
public class RegisterCourseTask {

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;
	
	@Inject
	private User user;

	@PersistenceContext
	private EntityManager entityManager;

	public void registerCourse(String taskId) {
		System.out.println("registerCourse...taskid: "+taskId);
		
		/*	
		businessProcess.startTask(taskId);

	 	//TODO: 
	   

		businessProcess.setVariable("approvalAction", "logout");
		businessProcess.completeTask();
		 */
	}

}
