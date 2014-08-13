package com.SEIS771.proj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Staff {
	
	@Id
	@Column(length=30)
	private String type;

	public String getType() {
		return type;
	}
	public void setType(String _type) {
		this.type = _type;
	}
}
