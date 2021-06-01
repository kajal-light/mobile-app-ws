package com.kajal.mobile.app.ws.ui.model.response;

import java.util.List;

public class UserRest {
	private String userid;
	private String firstname;

	private String lastname;
	private String email;
	
	private List<AddressesRest> addresses;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AddressesRest> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressesRest> addresses) {
		this.addresses = addresses;
	}
	

}
