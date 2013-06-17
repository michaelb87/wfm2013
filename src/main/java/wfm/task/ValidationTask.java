package wfm.task;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import wfm.bean.User;
import wfm.db.Course;

@Stateful
@Named
@ConversationScoped
public class ValidationTask implements JavaDelegate{

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private Course course;
	
	@Inject
	private User user;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Execution " + execution.getId() + " was executed.");
		
		if(validateMembership() && validateCapacity())
		{
			//TODO: weiter zu twitter und send notification mail
		}
		else{
			//TODO: zurueck zur courseList und fehlermeldung ob class full oder membership not eligible
		}
		
		
	}
	
	public boolean validateMembership(){
		
		//TODO: Kontrolle
		System.out.println("entitmanager is: "+entityManager+" business process: "+businessProcess);
/*
			Query q = entityManager
					.createNativeQuery("SELECT group_ID_  FROM ACT_ID_MEMBERSHIP a WHERE a.USER_ID_ ='"
							+ user.getUsername() + "'");

			@SuppressWarnings("unchecked")
			List<String> userGroups = q.getResultList();
			if (userGroups.contains(course.getMemberType())) {
				return true;
			} */

			return false;
	}
	
	public boolean validateCapacity(){
		
		//TODO: anzahl aller member die zu dem course subscribed sind (SQL aus zwischentabelle course-members) muss <= course.getMaxMembers() sein
		
		return false;		
	}

}
