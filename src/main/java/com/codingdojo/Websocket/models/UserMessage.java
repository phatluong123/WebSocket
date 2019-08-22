package com.codingdojo.Websocket.models;

import java.util.Set;

public class UserMessage implements Message{
	private Set<String> users= null;

	public UserMessage(Set<String> users) {
		this.users = users;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}
	
}
