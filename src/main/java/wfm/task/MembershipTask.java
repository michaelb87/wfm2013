package wfm.task;

import javax.inject.Inject;
import javax.inject.Named;

import wfm.bean.User;

@Named
public class MembershipTask {

	@Inject
	private User user;
	
	public void validate(){
		String membership = user.getMembership();
		if(membership.equals("bronze")){
			System.out.println("The user has access to courses with bronze restriction only");
		}
		else if(membership.equals("silver")){
			System.out.println("The user has access to courses with bronze and silver restriction only");
		}
		else
			System.out.println("The user has access to all courses");
	}
}
