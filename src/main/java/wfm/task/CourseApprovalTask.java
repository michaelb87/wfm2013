package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.CompleteTask;

@Stateful
@Named
@ConversationScoped
public class CourseApprovalTask {
	
	@Inject
	private BusinessProcess businessProcess;
	
	@CompleteTask 
	public void goToAddCourse() {
		businessProcess.setVariable("approvalAction", "add");
		
	}

}
