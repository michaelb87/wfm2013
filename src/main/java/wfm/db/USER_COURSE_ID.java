package wfm.db;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;



@Embeddable
public class USER_COURSE_ID implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ACT_ID_USER user;
	private Course course;

	@ManyToOne
	public ACT_ID_USER getUser() {
		return user;
	}

	public void setUser(ACT_ID_USER user) {
		this.user= user;
	}

	@ManyToOne
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course= course;
	}



}
