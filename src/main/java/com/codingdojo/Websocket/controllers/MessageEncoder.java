package com.codingdojo.Websocket.controllers;

import java.util.Iterator;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.codingdojo.Websocket.models.ChatMessage;
import com.codingdojo.Websocket.models.Message;
import com.codingdojo.Websocket.models.UserMessage;

public class MessageEncoder implements Encoder.Text<Message>{

	@Override
	public void init(EndpointConfig endpointConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Message message) throws EncodeException {
		String returnString = null;
		if (message instanceof ChatMessage) {
			ChatMessage chatMessage = (ChatMessage) message;
			returnString = Json.createObjectBuilder().add("messageType", chatMessage.getClass().getSimpleName())
					.add("name", chatMessage.getName())
					.add("message", chatMessage.getMessage())
					.add("location", chatMessage.getLocation())
					.build().toString();
		}
		else if (message instanceof UserMessage) {
			
			UserMessage usersMessage = (UserMessage) message;
			returnString = buildJsonUsersData(usersMessage.getUsers(), usersMessage.getClass().getSimpleName() );
		}
		return returnString;
	}
	
	private String buildJsonUsersData(Set<String> set , String messageType) {
		Iterator<String>iterator = set.iterator();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		while (iterator.hasNext()) jsonArrayBuilder.add(iterator.next());
	
		return Json.createObjectBuilder().add("messageType", messageType).add("user", jsonArrayBuilder).build().toString();
		
	}

}
