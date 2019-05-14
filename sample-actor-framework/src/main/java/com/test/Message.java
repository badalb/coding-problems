package com.test;

public class Message implements IMessage {
	private String sender;
	private String destination;
	private Object content;

	public Message(final String fromWhom, final String toWhom, Object what) {
		sender = fromWhom;
		destination = toWhom;
		content = what;
	}

	public Message(final String whereTo, Object what) {
		this(null, whereTo, what);
	}

	public Object getContent() {
		return (content);
	}

	public String getDestination() {
		return (destination);
	}

	public String getSender() {
		return (sender);
	}
}