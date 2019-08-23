package com.codingdojo.Websocket.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.stereotype.Component;

import com.codingdojo.Websocket.models.ChatMessage;
import com.codingdojo.Websocket.models.Message;
import com.codingdojo.Websocket.models.UserMessage;


@Component
@ServerEndpoint(value="/chatServerEndPoint", encoders= {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class ChatServerEndPoint {
	
	public static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
	@OnOpen
	public void handleOpen(Session userSession) throws IOException, EncodeException {
		chatroomUsers.add(userSession);		
		System.out.println("entered handleOpen. next step: add userSession to chatroomUsers");
		System.out.println("number of user = " + chatroomUsers.size());
	}
	
	@OnMessage
	public void handleMessage(Message incomingMessage, Session userSession) throws IOException, EncodeException {
		System.out.println("entered handleMessage");
		if (incomingMessage instanceof ChatMessage) {
			System.out.println("message sent: " + incomingMessage.toString());
			ChatMessage incomingChatMessage = (ChatMessage) incomingMessage;
			ChatMessage outgoingChatMessage = new ChatMessage();
			String username = (String) userSession.getUserProperties().get("username");
			if (username == null) {
				userSession.getUserProperties().put("username", incomingChatMessage.getMessage());
				outgoingChatMessage.setName("system");
				outgoingChatMessage.setLocation("California , US");
				outgoingChatMessage.setMessage("you are now connected as "+incomingChatMessage.getMessage());
				userSession.getBasicRemote().sendObject(outgoingChatMessage);
			}
			else {
				outgoingChatMessage.setName(username);
				outgoingChatMessage.setLocation(incomingChatMessage.getLocation());
				outgoingChatMessage.setMessage(incomingChatMessage.getMessage());
				Iterator<Session> iterator = chatroomUsers.iterator();
				while (iterator.hasNext()) iterator.next().getBasicRemote().sendObject(outgoingChatMessage);
			}
			Iterator<Session> iterator = chatroomUsers.iterator();
			while (iterator.hasNext()) iterator.next().getBasicRemote().sendObject(new UserMessage(getIds()));
		}
	}
	
	
	@OnClose
	public void handleClose(Session userSession) throws IOException, EncodeException {
		chatroomUsers.remove(userSession);
		Iterator<Session> iterator = chatroomUsers.iterator();
		while (iterator.hasNext()) iterator.next().getBasicRemote().sendObject(new UserMessage(getIds()));
	}
	
	public static Set<String> getIds(){
		HashSet<String> returnSet = new HashSet<String>();
		Iterator<Session> iterator = chatroomUsers.iterator();
		while (iterator.hasNext()) returnSet.add(iterator.next().getUserProperties().get("username").toString());
		
		
		return returnSet;
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("error");
		System.out.println(throwable.getClass().toString());
		System.out.println(throwable.getStackTrace().toString());
		System.out.println(throwable.getLocalizedMessage());
		System.out.println(throwable.getMessage());
	}
	
	
}