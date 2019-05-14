package com.test;

import java.util.Queue;

public class Broker {
	// ds
	Exchange exchange;

	Broker(Exchange e, Producer p) {
		this.exchange = e;
		this.exchange.p = p;
	}

	void bindToExchange(Queue q, Consumer c, String rq) {
		MyQueue mq = new MyQueue(rq, q, c);
		exchange.bindConsumer(mq);
	}
}
