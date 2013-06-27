package wfm.task;

import java.sql.Timestamp;

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
import wfm.db.Course;
import wfm.db.ACT_ID_USER;
import wfm.db.USER_COURSE;
import wfm.db.USER_COURSE_ID;


@Stateful
@Named
@ConversationScoped
public class ApproveCourseTask {

	private static final Logger log = LoggerFactory.getLogger(ApproveCourseTask.class);

	@Inject
	private BusinessProcess businessProcess;

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private ApproveCourseBean approveBean;


	private Course courseToApprove;
	private ACT_ID_USER userToApprove;


	public void approveCourse(String taskId) {

		businessProcess.startTask(taskId);

		try{
			USER_COURSE userCourse = new USER_COURSE();
			USER_COURSE_ID userCourseId = new USER_COURSE_ID();
			userCourseId.setCourse(courseToApprove);
			userCourseId.setUser(userToApprove);
			userCourse.setPk(userCourseId);
			userCourse.setProcessinstanceID(businessProcess.getProcessInstanceId());

			courseToApprove.getUserCourse().add(userCourse);
			userToApprove.getUserCourse().add(userCourse);

			
			String query = "INSERT INTO USER_COURSE (PROCESSINSTANCEID, COURSE_NR,USER_ID) "
					+"VALUES ('"+businessProcess.getProcessInstanceId()+"',"+courseToApprove.getCourse_nr()+",'"+userToApprove.getId_()+"')";
			
			log.info("insert query: "+query);
			entityManager.createNativeQuery(query).executeUpdate();
	  


			businessProcess.setVariable("approvalAction", "back");
			businessProcess.setVariable("approved", true);


			//--------------------------------------------------------------------
			//course needed for TimerEvent and Trainer notification
			Course registeredCourse = businessProcess.getVariable("courseToApprove"); 

			//TimerEvent functionality

			Timestamp t = (Timestamp) registeredCourse.getDate();
			log.info("Date of course: "+t);

			long old = t.getTime();
			long minutesToSubtract = 5;  //minutes before the actual start of the course
			long neu = minutesToSubtract*60*1000;		

			Timestamp timer = new Timestamp(old-neu);

			log.info("Date of course for timerEvent: "+timer);
			businessProcess.setVariable("timerEventTime", timer);

			//course took place event

			log.info("Date of course for courseEventTime: "+registeredCourse.getDate());
			businessProcess.setVariable("courseEventTime", registeredCourse.getDate());

			//---------------------------------------------------------------------------

			//needed for trainer notification mail
			ACT_ID_USER trainerToBeNotified = entityManager.find(ACT_ID_USER.class, registeredCourse.getTrainer());
			log.info("Trainermail: "+trainerToBeNotified.getEmail_());		
			businessProcess.setVariable("trainer", trainerToBeNotified.getEmail_());
			//---------------------------------------------------------------------------
			businessProcess.completeTask();
			//variables for messages
			businessProcess.setVariable("courseAction", "approved");
			businessProcess.setVariable("courseFromAction", courseToApprove.getName());

		}catch(Exception e){
			log.error("Error ApproveCourseTask: "+e.getMessage());
		}
	}

	public void rejectCourse(String taskId) {
		String cname = ((Course) businessProcess.getVariable("courseToApprove")).getName();
		businessProcess.startTask(taskId);
		businessProcess.setVariable("approvalAction", "back");
		businessProcess.setVariable("deletedCourseName", cname);
		businessProcess.setVariable("approved", false);
		businessProcess.completeTask();

		//variables for messages
		businessProcess.setVariable("courseAction", "rejected");
		businessProcess.setVariable("courseFromAction", cname);
	}


	public String logout() {
		Course registeredCourse = businessProcess.getVariable("courseToApprove");
		businessProcess.getTask().setAssignee(registeredCourse.getTrainer());
		log.info("setting task assigne for: "+ businessProcess.getTask().getName() + " to " +  registeredCourse.getTrainer());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}

	public Course getCourseToApprove() {
		courseToApprove = businessProcess.getVariable("courseToApprove");			
		return courseToApprove;
	}

	public void setCourseToApprove(Course courseToApprove) {
		this.courseToApprove = courseToApprove;
	}

	public ACT_ID_USER getUserToApprove() {
		userToApprove = businessProcess.getVariable("userToApprove");

		return userToApprove;
	}

	public void setUserToApprove(ACT_ID_USER userToApprove) {
		this.userToApprove = userToApprove;
	}

}
