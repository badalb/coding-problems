package com.test;

public interface FloorChooserStrategy {
	public int getNextFloor();

	public int getCost(int floor);

	public void addFloor(int floor);

	public boolean hasMore();

	public void notify(ElevatorStatus status);
}
