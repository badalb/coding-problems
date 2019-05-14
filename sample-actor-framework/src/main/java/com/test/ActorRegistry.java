package com.test;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorRegistry extends Actor {

	private HashMap<String, LinkedBlockingQueue<Object>> lookupTable;

	ActorRegistry() {
		super("_sal_registry_");
		lookupTable = new HashMap<String, LinkedBlockingQueue<Object>>();
	}

	public void send(final IMessage msg) {
		if (msg != null) {
			try {
				queue.put(msg);
			} catch (InterruptedException ie) {
				System.out.println("Interrupted Exception in ActorRegistry.send()");
			}
		}
	}

	public void addActor(String actorName, LinkedBlockingQueue<Object> actorQueue) {
		synchronized (this) {
			lookupTable.put(actorName, actorQueue);
		}
	}

	public void removeActor(final String actorName) {
		send(new Message(Commands.REMOVE_ACTOR, actorName));
	}

	@Override
	public void processMessage(final Object obj) {
		if (obj == null) {
			return;
		}

		IMessage msg = (IMessage) obj;

		if (msg.getSender() != null) {
			if (msg.getSender().startsWith("_registry_")) {
				processAdminTask(msg);
				return;
			}
		}

		if (msg.getDestination() != null) {
			if (msg.getDestination().startsWith("_registry_")) {
				processAdminTask(msg);
				return;
			}
		}

		addMessageToActorQueue(msg);
	}

	private void addMessageToActorQueue(final IMessage msg) {
		LinkedBlockingQueue<Object> actorQueue = lookupTable.get(msg.getDestination());
		if (actorQueue != null) {
			synchronized (this) {
				actorQueue.add(msg.getContent());
			}
		} else {
			System.err.println("Sal Error: actor " + msg.getDestination() + " not registered");
		}
	}

	private void processAdminTask(final IMessage msg) {
		if (msg.getDestination().equals(Commands.REMOVE_ACTOR)) {
			removeActorFromLookupTable((String) msg.getContent());
		} else if (msg.getDestination().equals(Commands.REGISTRY_SHUTDOWN)) {
			running = false;
		}
	}

	private void addActorToLookupTable(final Actor actor) {
		if (actor == null) {
			return;
		}

		String actorName = actor.getName();
		LinkedBlockingQueue<Object> actorQueue = actor.getQueue();

		synchronized (this) {
			lookupTable.put(actorName, actorQueue);
		}
	}

	private void addActorToLookupTable(final String actorName, final Object actorQueueObject) {
		if (actorName == null || actorQueueObject == null) {
			return;
		}

		LinkedBlockingQueue<Object> actorQueue = (LinkedBlockingQueue<Object>) actorQueueObject;

		synchronized (this) {
			lookupTable.put(actorName, actorQueue);
		}
	}

	private void removeActorFromLookupTable(final String actorName) {
		if (actorName == null) {
			return;
		}

		synchronized (this) {
			lookupTable.remove(actorName);
		}
	}

	@Override
	public void shutdown() {
		send(new Message(Commands.REGISTRY_SHUTDOWN, Commands.SHUTDOWN));
	}

	public int getSize() {
		return (lookupTable.size());
	}
}
