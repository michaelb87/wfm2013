package wfm.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import wfm.db.Course;

@XmlRootElement 
public class ItemEntry implements Serializable
{

	private static final long serialVersionUID = 7989434282534825620L;
	private Course course = null;
	private boolean selected = false;
	
	public ItemEntry()
	{
		
	}
	
	public Course getCourse() { return this.course; }
	public boolean getSelected() { return this.selected; }
	
	public void setCourse(Course course) { this.course = course; }
	public void setSelected(boolean selected) { this.selected = selected; }
}
