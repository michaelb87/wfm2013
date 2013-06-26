package wfm.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named
@SessionScoped
public class ApproveCourseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	 	
	@PersistenceContext 
	EntityManager entityManager;

	private boolean trainer = false;
	

	public boolean isTrainer() {
		return trainer;
	}

	public void setTrainer(boolean trainer) {
		this.trainer = trainer;
	}

}



