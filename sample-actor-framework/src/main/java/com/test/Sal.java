package com.test;

public class Sal {

	private ActorRegistry registry = null;

	public ActorRegistry initialize() {
		registry = new ActorRegistry();
		Thread registryThread = new Thread(registry);
		registryThread.start();
		return (registry);
	}

	public void addActor(Actor actor) {
		registry.addActor(actor.getName(), actor.getQueue());
		actor.setRegistry(registry);
		new Thread(actor).start();
	}

	public void shutdown() {
		if (registry != null) {
			registry.shutdown();
		}
	}
}
