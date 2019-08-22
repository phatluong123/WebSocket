package com.codingdojo.Websocket.controllers;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.codingdojo.Websocket.models.ChatMessage;
import com.codingdojo.Websocket.models.Message;

public class MessageDecoder implements Decoder.Text<Message>{

	@Override
	public void init(EndpointConfig endpointConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message decode(String jsonMessage) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
		JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
		chatMessage.setMessage(jsonObject.getString("message"));
		chatMessage.setMessage(jsonObject.getString("location"));
		return chatMessage;
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		boolean flag = true;
		
		try { Json.createReader(new StringReader(jsonMessage)).readObject() ;}
		catch (Exception e) {flag = false;}
		return flag;
	}
	
}
