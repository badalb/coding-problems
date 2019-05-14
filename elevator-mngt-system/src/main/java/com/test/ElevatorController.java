package com.test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorController {
	private final ElevatorChooserStrategy elevatorChooser;
	private final ArrayList<Elevator> elevators;
	private static ElevatorController INSTANCE;
	private static final Object lockObj = new Object();
	private static ExecutorService executor;

	private ElevatorController(ElevatorChooserStrategy elevatorChooser, ArrayList<Elevator> elevators) {
		this.elevatorChooser = elevatorChooser;
		this.elevators = elevators;
	}

	public static ElevatorController getInstance(ElevatorChooserStrategy elevatorChooser,
			ArrayList<Elevator> elevators) {
		if (INSTANCE != null)
			return INSTANCE;

		synchronized (lockObj) {
			if (INSTANCE == null) {
				INSTANCE = new ElevatorController(elevatorChooser, elevators);
				executor = Executors.newFixedThreadPool(elevators.size());
				for (Elevator e : elevators) {
					executor.submit(e);
				}
			}
		}

		return INSTANCE;
	}

	public void callElevator(int floor) {
		Elevator bestElevator = elevatorChooser.getBestElevator(floor);
		callElevator(floor, bestElevator);
	}

	private void callElevator(int floor, Elevator elevator) {
		elevator.gotoFloor(floor);
	}

	public void callElevator(int floor, int elevator) {
		Elevator e = getElevator(elevator);
		if (e == null)
			return;
		callElevator(floor, e);
	}

	private Elevator getElevator(int elevator) {
		if (elevator < 0 || elevator >= elevators.size())
			return null;
		return elevators.get(elevator);
	}

	public void setMaintenanceMode(int elevator) {
		Elevator e = getElevator(elevator);
		if (e == null)
			return;
		e.setMaintenance();
	}

	public void setBroken(int elevator) {
		Elevator e = getElevator(elevator);
		if (e == null)
			return;
		e.setBroken();
	}

	public void resetElevator(int elevator) {
		Elevator e = getElevator(elevator);
		if (e == null)
			return;
		e.resetElevator();
	}
}