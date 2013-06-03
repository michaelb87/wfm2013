package wfm.task;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.ActivitiCdiException;
import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.CompleteTask;

@Stateful
@Named
@ConversationScoped
public class CourseApprovalTask {
	
	@Inject
	private BusinessProcess businessProcess;
	
	public void goToAddCourse() {
		System.out.println("going to add course soon... hope we can complete??");
		businessProcess.setVariable("approvalAction", "add");
		try {
		businessProcess.completeTask();
		} catch(ActivitiCdiException e) {
			System.out.println("no such task exception @ courseApprovalTask");
		}
		
	}

}
