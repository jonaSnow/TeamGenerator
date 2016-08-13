package com.guzman.projects.teamgenerator.teamgen;

public class Member {

	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
			this.lastName = lastName;
	}

	public String toString() {
		return String.format("%s %s", firstName, lastName);
	}
}