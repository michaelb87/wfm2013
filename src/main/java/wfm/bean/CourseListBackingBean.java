package wfm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import wfm.bean.ItemEntry;
import wfm.db.Course;

@Named
@RequestScoped
@ManagedBean(name="courses")
public class CourseListBackingBean implements Serializable{

	private static final long serialVersionUID = 1962753564688979487L;
	/*	private String instanceId = "";
	private String taskId = "";
	private String page = "";*/

	private List<ItemEntry> items;
	private List<ItemEntry> personalItems; //for showing up in the delete courses screen


	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private User user;

	public List<ItemEntry> getItems(){

		Query query = entityManager.createQuery("SELECT c FROM Course c");
		@SuppressWarnings("unchecked")
		List<Course> courses =  query.getResultList();
		System.out.println("Anzahl Kurse: "+courses.size());

		items = new ArrayList<ItemEntry>();
		ItemEntry e;
		Course c;
		for(int i= 0; i<courses.size(); i++){
			e = new ItemEntry();
			c = courses.get(i);
			e.setCourse(c);
			items.add(e);
		}

		return (ArrayList<ItemEntry>) items;
	}
	public void setItems(List<ItemEntry> items)
	{
		this.items = items;
	}
	
	public List<ItemEntry> getPersonalItems(){
		
		System.out.println("Username for showing personal courses: "+user.getUsername());
		
		Query query = entityManager.createQuery("SELECT c FROM Course c WHERE c.trainer='"+user.getUsername()+"'");
		@SuppressWarnings("unchecked")
		List<Course> courses =  query.getResultList();
		System.out.println("Anzahl Kurse: "+courses.size());

		personalItems = new ArrayList<ItemEntry>();
		ItemEntry e;
		Course c;
		for(int i= 0; i<courses.size(); i++){
			e = new ItemEntry();
			c = courses.get(i);
			e.setCourse(c);
			personalItems.add(e);
		}

		return (ArrayList<ItemEntry>) personalItems;
	}
	public void setPersonalItems(List<ItemEntry> personalItems)
	{
		this.personalItems = personalItems;
	}

	/*public String getPage() { return page; }
	public void setPage(String page) { this.page = page; }

	public String getInstanceId() { return this.instanceId; }
	public void setInstanceId(String instanceId) { this.instanceId = instanceId; }

	public String getTaskId() { return this.taskId; }
	public void setTaskId(String taskId) { this.taskId = taskId; }
	 */

}
