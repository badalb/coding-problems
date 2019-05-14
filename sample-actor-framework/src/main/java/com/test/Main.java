package com.test;

public class Main {

	public static void main(String[] args) {
		Sal sal = new Sal();
		ActorRegistry central = sal.initialize();

		sal.addActor(new Stdout("stdout"));

		central.send(new Message("stdout", "hello, world!"));
		central.send(new Message("stdout", "hello again, world"));
		central.send(new Message("stdout", Commands.SHUTDOWN));

		sal.shutdown();
	}
}
