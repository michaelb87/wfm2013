package wfm.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class NotifyTrainerAboutWeather implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
				
		System.out.println("you should notify the _trainer_ about the bad weather!");
		
	}
	
}