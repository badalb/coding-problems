package com.test;

import java.util.concurrent.TimeUnit;

public class ConcreteElevator extends Elevator {
	private final int id;
	private final FloorChooserStrategy floorChooser;
	private final ElevatorStatus status = new ElevatorStatus(0, ElevatorStatus.State.STOPPED);

	ConcreteElevator(int id, FloorChooserStrategy floorChooser) {
		this.id = id;
		this.floorChooser = floorChooser;
	}

	@Override
	public ElevatorStatus getStatus() {
		return status;
	}

	@Override
	public void setMaintenance() {
		log("In Maintenance Mode.");
		status.setState(ElevatorStatus.State.MAINTENANCE);
		floorChooser.notify(status);
	}

	@Override
	public void setBroken() {
		log("In Broken Mode.");
		status.setState(ElevatorStatus.State.BROKEN);
		floorChooser.notify(status);
	}

	@Override
	public void resetElevator() {
		log("In Stopped Mode.");
		status.setState(ElevatorStatus.State.STOPPED);
		floorChooser.notify(status);
	}

	@Override
	public void gotoFloor(int floor) {
		floorChooser.addFloor(floor);
		log("Adding Floor: " + floor);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int costToGoTo(int floor) {
		return floorChooser.getCost(floor);
	}

	private void moveTo(int floor) {
		if (status.getFloor() < floor) {
			status.setState(ElevatorStatus.State.GOING_UP);
		} else if (status.getFloor() > floor) {
			status.setState(ElevatorStatus.State.GOING_DOWN);
		} else {
			status.setState(ElevatorStatus.State.STOPPED);
		}

		status.setFloor(floor);
		floorChooser.notify(status);
		log("Moved to floor: " + floor + ", State = " + status.getState().name());
	}

	private void log(String s) {
		System.out.println("Elevator " + id + ": " + s);
	}

	@Override
	public void run() {
		log("Starting.");
		while (true) {
			if (!floorChooser.hasMore()) {
				try {
					TimeUnit.MILLISECONDS.sleep(250);
					continue;
				} catch (InterruptedException e) {
					// ...
				}
			}

			int nextFloor = floorChooser.getNextFloor();
			moveTo(nextFloor);
			if (!floorChooser.hasMore()) {
				status.setState(ElevatorStatus.State.STOPPED);
				floorChooser.notify(status);
			}
		}
	}
}
