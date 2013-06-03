package wfm.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Course implements Serializable{

	private static final long serialVersionUID = -2556217790431280277L;
	
	@Id
	@GeneratedValue
	private int course_nr;
	
	private String trainer; //who is responsible for the course
	private int maxMembers; //how many 
	private String memberType; //bronze, silver or gold course
	private boolean indoor; //true if indoor course, false if outdoor
	
	public int getCourse_nr() {
		return course_nr;
	}
	public void setCourse_nr(int course_nr) {
		this.course_nr = course_nr;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	public int getMaxMembers() {
		return maxMembers;
	}
	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public boolean isIndoor() {
		return indoor;
	}
	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}
	
	

}
