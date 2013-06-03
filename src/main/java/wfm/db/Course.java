package wfm.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course implements Serializable{

	private static final long serialVersionUID = -2556217790431280277L;
	
	@Id
	private int course_nr;
	
	

}
