package com.test;

public class Producer implements Runnable {

	Exchange exchange;

	String routingKey;

	Producer(Exchange e) {
		exchange = e;
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		// hasSent = true;
		int i = 0;
		Sleep.sleep();
		while (i < 100) {

			if (i % 2 == 0)
				exchange.addAndRoute(i, "even,number");
			else
				exchange.addAndRoute(i, "odd,number");
			System.out.println("prod .. " + i);
			i++;
		}
		System.out.println("prod done ");
	}
}