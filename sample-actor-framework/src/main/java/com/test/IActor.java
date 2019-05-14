package com.test;

public interface IActor {

	public void processMessage(Object msg);

	public void run();

	public void shutdown();
}
