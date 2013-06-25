package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wfm.bean.ApproveCourseBean;
import wfm.bean.User;
import wfm.db.ACT_ID_USER;
import wfm.db.Course;


@Stateful
@Named
@ConversationScoped
public class RegisterCourseTask {

	private static final Logger log = LoggerFactory.getLogger(LoginTask.class);


	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;

	@Inject
	private User user;

	@Inject
	private ApproveCourseBean approveBean;

	@PersistenceContext
	private EntityManager entityManager;

	public void registerCourse(String taskId, int courseId) {

		log.info("User: " + user.getUsername()+" CourseId: "+courseId);


		Course courseToApprove = entityManager.find(Course.class, courseId);	


		ACT_ID_USER userToApprove = entityManager.find(ACT_ID_USER.class, user.getUsername());



		businessProcess.setVariable("courseToApprove", courseToApprove);
		businessProcess.setVariable("userToApprove", userToApprove);

		businessProcess.setVariable("subscriptionAction", "subscribe");
		businessProcess.completeTask();
	}
	
	public String logout(String taskId) {
		
		log.info("user " + user.getUsername()+" is going to log out");
		businessProcess.startTask(taskId);
		businessProcess.setVariable("subscriptionAction", "logout");
		businessProcess.completeTask();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

}
