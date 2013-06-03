package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.BusinessProcess;

import wfm.bean.Course;

@Stateful
@Named
@ConversationScoped
public class addCourseTask {
	
	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private Course course;
	
	public void addCourse(String taskId) {
		businessProcess.startTask(taskId);
		
		System.out.println("Course: " + course);
		//add to database:
		
		
		businessProcess.completeTask();
	}

}
