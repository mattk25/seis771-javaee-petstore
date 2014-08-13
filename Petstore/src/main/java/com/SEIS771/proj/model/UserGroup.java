package com.SEIS771.proj.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
@Entity
@NamedQueries({
	@NamedQuery(name="getUserGroupById", query="SELECT g FROM UserGroup g Where g.id = :id"),
	@NamedQuery(name="getUserGroupByName", query = "SELECT g FROM UserGroup g Where g.name = :name")
})
public class UserGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=225)
	private String name;
	@Column(length=30)
	private String description;
	
	@XmlInverseReference(mappedBy="group")
	@OneToMany(mappedBy="group")
	  private List<Person> members;


    public UserGroup(String name, String description){
        this.name = name;
        this.description = description;
        this.members = new ArrayList<Person>();
    }

    public UserGroup(){
        this.members = new ArrayList<Person>();
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers(List<Person> members) {
		this.members = members;
	}

    public void addMember(Person person){
        this.members.add(person);
    }
}
