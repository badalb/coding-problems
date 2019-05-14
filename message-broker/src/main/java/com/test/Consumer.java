package com.test;

import java.util.Queue;

public class Consumer implements Runnable {
	public Queue<?> q;
	private String name;
	boolean hasReceived = false;

	public Consumer(String n) {
		name = n;
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		hasReceived = true;
		while (true) {
			Sleep.sleep();
			if (!q.isEmpty())
				System.out.println(name + " reading " + q.remove());
		}
	}
}