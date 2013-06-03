package wfm.uimediator;

import java.util.Map;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import wfm.bean.User;
import wfm.tx.Transactional;

/**
 * Specializes {@link BusinessProcess} bean to add generic code needed for
 * UiMediator
 */
@Specializes
public class UiMediatedBusinessProcessBean extends BusinessProcess {

	private static final long serialVersionUID = 1L;

	@Inject
	private Event<TaskCompletionEvent> taskCompletionEvent;

	@Inject
	private User user;

	@Inject
	private UIMediator uiMediator;

	@Override
	@Transactional
	// JPA and Activiti shall do it in the same transaction
	public void completeTask() {

		taskCompletionEvent.fire(new TaskCompletionEvent());
		System.out.println("yes it fired!!!!!!!! i am so happy");
		
		Task task = getTask();
		System.out.println("task: " + task.getName() + " / processid: " + task.getProcessInstanceId());
		super.completeTask();

		if (task != null) {
			uiMediator.checkProcessInstanceStatus(task.getAssignee(),
					task.getProcessInstanceId());
		}
		System.out.println(">>");
	}

	@Override
	public ProcessInstance startProcessByKey(String key,
			Map<String, Object> variables) {
		ProcessInstance processInstance = super.startProcessByKey(key,
				variables);
		uiMediator.checkProcessInstanceStatus(user.getUsername(),
				processInstance.getProcessInstanceId());
		return processInstance;
	}

}
