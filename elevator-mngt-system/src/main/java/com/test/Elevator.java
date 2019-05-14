package com.test;

public abstract class Elevator implements Runnable {
	public static Elevator createElevator(int id, int minFloor, int maxFloor, ElevatorStatus initialStatus) {
		return new ConcreteElevator(id, new CostBasedFloorChoserStrategy(minFloor, maxFloor, initialStatus));
	}

	public abstract ElevatorStatus getStatus();

	public abstract void setMaintenance();

	public abstract void setBroken();

	public abstract void resetElevator();

	public abstract void gotoFloor(int floor);

	public abstract int getId();

	public abstract int costToGoTo(int floor);
}