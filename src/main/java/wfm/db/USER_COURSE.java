package wfm.db;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.AssociationOverrides;
import javax.persistence.AssociationOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_course")
@AssociationOverrides({
    @AssociationOverride(name = "pk.user", 
        joinColumns = @JoinColumn(name = "user_id")),
    @AssociationOverride(name = "pk.course", 
        joinColumns = @JoinColumn(name = "course_nr")) })
public class USER_COURSE implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private USER_COURSE_ID pk = new USER_COURSE_ID();
	
	@EmbeddedId
    public USER_COURSE_ID getPk() { 
		return pk;
		
	}

	public void setPk(USER_COURSE_ID pk) {
		this.pk = pk;
	}

	
	/*@ManyToOne
    @JoinColumn//(name = "user_id")
    private ACT_ID_USER user;

    @ManyToOne
    @JoinColumn//(name = "course_nr")
    private Course course;*/
	
	
    @Column(name = "PROCESSINSTANCEID")
    private String processinstanceID;

	/*public ACT_ID_USER getUser() {
		return user;
	}

	public void setUser(ACT_ID_USER user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
*/
	public String getProcessinstanceID() {
		return processinstanceID;
	}


	public void setProcessinstanceID(String processinstanceID) {
		this.processinstanceID = processinstanceID;
	}
    
    
    

}


