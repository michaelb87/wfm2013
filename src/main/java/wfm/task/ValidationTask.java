package wfm.task;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wfm.db.ACT_ID_GROUP;
import wfm.db.ACT_ID_USER;
import wfm.db.Course;

public class ValidationTask implements JavaDelegate{

	private static final Logger log = LoggerFactory.getLogger(ValidationTask.class);

	private Course course;
	private ACT_ID_USER user;
	private String message = "";

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("ValidationTask " + execution.getId() + " was executed.");


		course = (Course) execution.getVariable("courseToApprove");
		user = (ACT_ID_USER) execution.getVariable("userToApprove");
		
		//TODO:

	/*	if(validateCapacity() && validateMembership())
		{*/
			log.info("validation ok");
			execution.setVariable("validation", "ok");
	/*	}
		else{
			log.info("validation failed");
			execution.setVariable("validation", "failed");
			execution.setVariable("failmessage", "Subscription failed! "+message);
		}	*/

	}
	
/*	public boolean validateCapacity(){

		if (course.getUsers().size()<course.getMaxMembers())
			return true;
		else
			message="The course '"+course.getName()+"' is already full. ";

		return false;		
	}

	public boolean validateMembership(){
		boolean ok = false;

		for (ACT_ID_GROUP g : user.getGroups()){
			if(g.getId_().equals(course.getMemberType()))
				ok = true;
		}
		
		if(ok==false)
			message="Your membership type is not eligible for the course you want to attend. ";

		return ok;
	}*/



}
