package com.test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBrokerDesign {

	public static void main(String[] args) {
		Exchange e = new Exchange("e1");
		Producer p = new Producer(e);
		Broker mb = new Broker(e, p);

		Queue odd = new ConcurrentLinkedQueue();
		Consumer oddC1 = new Consumer("odd");
		mb.bindToExchange(odd, oddC1, "odd");

		Queue even = new ConcurrentLinkedQueue();
		Consumer evenC1 = new Consumer("even");
		mb.bindToExchange(even, evenC1, "even");

		Queue number = new ConcurrentLinkedQueue();
		Consumer numberC = new Consumer("number");
		mb.bindToExchange(number, numberC, "number");
	}
}