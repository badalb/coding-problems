package com.test;

import java.util.ArrayList;

public class CostBasedElevatorChooserStrategy implements ElevatorChooserStrategy {
	private final ArrayList<Elevator> elevators;

	public CostBasedElevatorChooserStrategy(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}

	@Override
	public Elevator getBestElevator(int floor) {
		Elevator bestElevator = elevators.get(0);
		int minCost = bestElevator.costToGoTo(floor);
		for (int i = 1; i < elevators.size(); i++) {
			Elevator e = elevators.get(i);
			int cost = e.costToGoTo(floor);
			if (cost <= minCost && e.getStatus().getState() != ElevatorStatus.State.BROKEN
					&& e.getStatus().getState() != ElevatorStatus.State.MAINTENANCE) {
				minCost = cost;
				bestElevator = e;
			}
		}

		return bestElevator;
	}
}
