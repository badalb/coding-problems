package com.test;

public class Stdout extends Actor {

	public Stdout(final String assignedName) {
		super(assignedName);
	}

	@Override
	public void processMessage(final Object o) {
		assert (o != null);

		String msg;

		if (o instanceof String) {
			msg = (String) o;

			if (msg.isEmpty()) {
				return;
			}

			if (msg.equals(Commands.SHUTDOWN)) {
				super.shutdown();
			} else {
				System.out.println(msg);
			}
		}
	}
}
