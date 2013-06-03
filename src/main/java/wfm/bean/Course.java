package wfm.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

@Named
@SessionScoped
public class Course implements Serializable{
	
	private static final long serialVersionUID = 7576303891011573948L;
	
	private String id; 
	private String name;
	private String desc;
	private String memberType;
	private String type; //indoor , outdoor
	


	
	public Course(){
		
	}
	public Course(String id, String name, String desc, String type) {		
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
}
