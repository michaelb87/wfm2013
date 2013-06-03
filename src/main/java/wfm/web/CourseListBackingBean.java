package wfm.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import wfm.bean.ItemEntry;
import wfm.db.Course;

@Named
@SessionScoped
@ManagedBean(name="courses")
public class CourseListBackingBean implements Serializable{
	
	private static final long serialVersionUID = 1962753564688979487L;
	private String instanceId = "";
	private String taskId = "";
	private List<ItemEntry> items;
	private String page = "";
	
	public CourseListBackingBean()
	{
		items = new ArrayList<ItemEntry>();
		
		/*ItemEntry e1 = new ItemEntry();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Course 1");
		c1.setDesc("Description 1");
		c1.setType("indoor (bronze)");
		e1.setCourse(c1);
		items.add(e1);
		
		ItemEntry e2 = new ItemEntry();
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("Course 2");
		c2.setDesc("Description 2");
		c2.setType("indoor (silver)");
		e2.setCourse(c2);
		items.add(e2);
		
		ItemEntry e3 = new ItemEntry();
		Course c3 = new Course();
		c3.setId(3);
		c3.setName("Course 3");
		c3.setDesc("Description 3");
		c3.setType("indoor (gold)");
		e3.setCourse(c3);		
		items.add(e3);
		
		ItemEntry e4 = new ItemEntry();
		Course c4 = new Course();
		c4.setId(4);
		c4.setName("Course 4");
		c4.setDesc("Description 4");
		c4.setType("outdoor (gold)");
		e4.setCourse(c4);
		items.add(e4); */
		
	}
	
	public List<ItemEntry> getItems()
	{
		return this.items;
	}
	
	public void setItems(List<ItemEntry> items)
	{
		this.items = items;
	}
	
	public String getPage() { return page; }
	public void setPage(String page) { this.page = page; }
	
	public String getInstanceId() { return this.instanceId; }
	public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
	
	public String getTaskId() { return this.taskId; }
	public void setTaskId(String taskId) { this.taskId = taskId; }
	
}
