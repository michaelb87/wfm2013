package wfm.uimediator;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;

import wfm.bean.User;

@SessionScoped
@Named
public class TaskList implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private TaskService taskService;

  @Inject
  private FormService formService;
  
  @Inject
  private User user;

  public void update() {
    // do nothing here
  }

  public List<Task> getList() {
	  //this query is for testing purposes only! we list all "add course"-tasks assigned to the user... could also list all tasks assigned to group ect.
    return taskService.createTaskQuery().taskAssignee(user.getUsername()).taskName("add course").list();
  }

  public String getFormKey(Task task) {
    TaskFormData taskFormData = formService.getTaskFormData(task.getId());
    if (taskFormData!=null) {
      return taskFormData.getFormKey();
    }
    else {
      // we do not want to fail just because we have tasks in the task list without form data (typically manually created tasks)
      return null;
    }
  }
}
