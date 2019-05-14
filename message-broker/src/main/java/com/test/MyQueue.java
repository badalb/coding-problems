package com.test;

import java.util.Queue;

public class MyQueue {
	String routingKey;
	Queue q;
	private Consumer c;

	public MyQueue(String routingKey, Queue q, Consumer c) {
		this.routingKey = routingKey;
		this.q = q;
		this.c = c;
		c.q = q;
	}
}