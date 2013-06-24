package wfm.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ACT_ID_GROUP implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id_;

	private int rev_;
	private String name_;
	private String type_;


	@ManyToMany(fetch = FetchType.EAGER, mappedBy="groups")
	private Set<ACT_ID_USER> users = new HashSet<ACT_ID_USER>();


	public Set<ACT_ID_USER> getUsers() {
		return users;
	}


	public void setUsers(Set<ACT_ID_USER> users) {
		this.users = users;
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


	public String getName_() {
		return name_;
	}


	public void setName_(String name_) {
		this.name_ = name_;
	}


	public String getType_() {
		return type_;
	}


	public void setType_(String type_) {
		this.type_ = type_;
	}

}
