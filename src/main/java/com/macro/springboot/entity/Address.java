package com.macro.springboot.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "[Address]")
public class Address {
	@Id
	@Column(name = "id")
	int id;
	@Column(name = "user_id")
	int userId;
	@Column(name = "address")
	String address;
	@Column(name = "enabled")
	boolean enabled;
	@Transient
	private String other;

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}

	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}

	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}

	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
}
