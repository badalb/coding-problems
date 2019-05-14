package com.test;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor implements IActor, Runnable {

	LinkedBlockingQueue<Object> queue;
	private String name;
	private ActorRegistry registry;
	boolean running = true;

	public Actor(final String assignedName) {
		assert (assignedName != null);
		queue = new LinkedBlockingQueue<Object>();
		name = assignedName;
	}

	public void run() {
		Object msg;

		while (running) {
			try {
				msg = queue.take();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
				continue;
			}

			if (isShutdown(msg)) {
				return;
			}

			processMessage(msg);
		}
	}

	public void processMessage(final Object msg) {

	}

	private boolean isShutdown(Object msg) {
		assert (msg != null);

		if (msg.equals(Commands.SHUTDOWN)) {
			return (true);
		}

		return (false);
	}

	public void shutdown() {
		registry.removeActor(name);
		queue.add(Commands.SHUTDOWN);
	}

	public String getName() {
		return (name);
	}

	public LinkedBlockingQueue<Object> getQueue() {
		return (queue);
	}

	public void setRegistry(final ActorRegistry reg) {
		registry = reg;
	}
}
