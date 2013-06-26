package wfm.uimediator;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequestScoped
@Named("uiMediator")
public class UIMediator implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(UIMediator.class);

	private static final long serialVersionUID = 1L;

	@Inject
	private TaskService taskService;

	@Inject
	private FormService formService;

	private String nextTaskId;

	private String nextTaskForm;

	/**
	 * check for new task in same process for same user
	 */
	public void checkProcessInstanceStatus(String assignee, String processInstanceId) {
		nextTaskId = null;
		nextTaskForm = null;
		if (processInstanceId != null && assignee != null) {
			List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId).list();
			if (tasks.size() == 1) {
				nextTaskId = tasks.get(0).getId();
				nextTaskForm = formService.getTaskFormData(nextTaskId).getFormKey();
			}
		}

		// Possible addition: wait for a defined time if something pops up.

		if (nextTaskForm!=null) {
			log.info("Mediator decided to route to task form " + nextTaskForm);      
		} else {
			log.info("Mediator decided to route back to task list, nothing to do immediateley");      
		}
	}
	public String getNextTaskId() {
		return nextTaskId;
	}

	public String getNextTaskForm() {
		return nextTaskForm;
	}

}
