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
public class addCourseTask {

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;
	
	@Inject
	private User user;

	@PersistenceContext
	private EntityManager entityManager;

	public void addCourse(String taskId) {
		businessProcess.startTask(taskId);
		
		
		System.out.println("User: " + user.getUsername());
		System.out.println("Course: " + course.toString());
		// add to database:
		
		Course coursePersist = new Course();
		coursePersist.setTrainer(user.getUsername());
		coursePersist.setName(course.getName());
		coursePersist.setDesc(course.getDesc());
		coursePersist.setMemberType(course.getMemberType());
		coursePersist.setIndoor(course.isIndoor());
		coursePersist.setMaxMembers(course.getMaxMembers());
	    
	    entityManager.persist(coursePersist);
	    entityManager.flush();

		businessProcess.setVariable("approvalAction", "logout");
		businessProcess.completeTask();
	}

}
