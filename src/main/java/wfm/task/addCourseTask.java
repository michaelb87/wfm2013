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
public class addCourseTask {
	
	private static final Logger log = LoggerFactory.getLogger(addCourseTask.class);

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;
	
	@Inject
	private User user;

	@PersistenceContext
	private EntityManager entityManager;

	public void addCourse(String taskId) {
		
		
		log.info("User: " + user.getUsername()+" Course: " + course.toString());
		// add to database:
		
		Course coursePersist = new Course();
		coursePersist.setTrainer(user.getUsername());
		coursePersist.setName(course.getName());
		coursePersist.setDesc(course.getDesc());
		coursePersist.setMemberType(course.getMemberType());
		coursePersist.setIndoor(course.isIndoor());
		coursePersist.setMaxMembers(course.getMaxMembers());
		coursePersist.setLocation(course.getLocation());
		coursePersist.setDate(course.getDate());
	    
	    entityManager.persist(coursePersist);
	    entityManager.flush();	    
	    
	    //ACT_ID_USER trainerToBeNotified = entityManager.find(ACT_ID_USER.class, user.getUsername());
	    
	    //log.info("zzz: "+trainerToBeNotified);
	    //businessProcess.setVariable("trainer", trainerToBeNotified);   // Works, trainer is saved correctly, but getVariable gets member instead of trainer Oo
		businessProcess.setVariable("approvalAction", "logout");
		businessProcess.completeTask();
	}

}
