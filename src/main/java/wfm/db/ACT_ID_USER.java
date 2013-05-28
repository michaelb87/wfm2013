package wfm.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;



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
