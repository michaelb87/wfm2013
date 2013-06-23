package wfm.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class RewardUserTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("You get aber sowas von very much reward points - CONGRATULATIONS");
		
	}

}
