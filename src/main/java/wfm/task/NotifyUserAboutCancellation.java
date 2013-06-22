package wfm.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class NotifyUserAboutCancellation implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
				
		System.out.println("you should notify the user about the course cancellation now!");
		
	}
	
}