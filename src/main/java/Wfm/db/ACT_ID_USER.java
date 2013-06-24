package wfm.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;



@Entity
public class ACT_ID_USER implements Serializable{

	private static final long serialVersionUID = 7123L;

	@Id
	private String id_;

	private int rev_;
	private String first_;
	private String last_;
	private String email_;
	private String pwd_;
	private String picture_id;
	//----experimental .. reward points
	
	@Column
	private Integer rewardPoints;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable( name = "user_course",
	joinColumns = {@JoinColumn (name = "user_id")}, 
	inverseJoinColumns = {@JoinColumn(name = "course_nr")})
	private Set<Course> courses = new HashSet<Course>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable( name = "ACT_ID_MEMBERSHIP",
	joinColumns = {@JoinColumn (name = "USER_ID_")}, 
	inverseJoinColumns = {@JoinColumn(name = "GROUP_ID_")})
	private Set<ACT_ID_GROUP> groups = new HashSet<ACT_ID_GROUP>();


	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Integer getRewardPoints() {
		return this.rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Set<ACT_ID_GROUP> getGroups() {
		return groups;
	}

	public void setGroups(Set<ACT_ID_GROUP> groups) {
		this.groups = groups;
	}

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}


	public int getRev_() {
		return rev_;
	}



	public void setRev_(int rev_) {
		this.rev_ = rev_;
	}



	public String getFirst_() {
		return first_;
	}



	public void setFirst_(String first_) {
		this.first_ = first_;
	}



	public String getLast_() {
		return last_;
	}



	public void setLast_(String last_) {
		this.last_ = last_;
	}



	public String getEmail_() {
		return email_;
	}



	public void setEmail_(String email_) {
		this.email_ = email_;
	}



	public String getPwd_() {
		return pwd_;
	}



	public void setPwd_(String pwd_) {
		this.pwd_ = pwd_;
	}



	public String getPicture_id() {
		return picture_id;
	}



	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}



	@Override
	public String toString() {
		return "User [userName=" + id_ + ", password=" + pwd_ + "]";
	}


}
