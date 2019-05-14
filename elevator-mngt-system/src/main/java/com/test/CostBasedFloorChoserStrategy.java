package com.test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class CostBasedFloorChoserStrategy implements FloorChooserStrategy {
	private PriorityQueue<Integer> upQueue = new PriorityQueue<>();
	private PriorityQueue<Integer> downQueue = new PriorityQueue<>(Comparator.reverseOrder());
	private int minFloor;
	private int maxFloor;
	private ElevatorStatus status;

	private final int COST_PER_STOP = 1;
	private final int COST_PER_VISIT = 1;

	public CostBasedFloorChoserStrategy(int minFloor, int maxFloor, ElevatorStatus initialStatus) {
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
		this.status = initialStatus;
	}

	@Override
	public int getNextFloor() {
		if (status.getState() == ElevatorStatus.State.GOING_UP && !upQueue.isEmpty()) {
			return upQueue.poll();
		} else if (status.getState() == ElevatorStatus.State.GOING_DOWN && !downQueue.isEmpty()) {
			return downQueue.poll();
		} else if (!downQueue.isEmpty()) {
			return downQueue.poll();
		} else if (!upQueue.isEmpty()) {
			return upQueue.poll();
		}

		return minFloor;
	}

	@Override
	public int getCost(int floor) {
		if (floor < minFloor || floor > maxFloor) {
			return Integer.MAX_VALUE;
		}
		if (status.getState() == ElevatorStatus.State.BROKEN || status.getState() == ElevatorStatus.State.MAINTENANCE) {
			return Integer.MAX_VALUE;
		}

		if (status.getFloor() == floor) {
			return 0;
		}

		int numStops = getNumStops(floor);
		int numVisits = getNumVisits(floor);
		return (numStops * COST_PER_STOP) + (numVisits * COST_PER_VISIT);
	}

	private int getNumStops(int floor) {
		return 0;
	}

	private int getNumVisits(int floor) {
		int currentFloor = status.getFloor();
		ElevatorStatus.State currentState = status.getState();
		int absDistance = Math.abs(currentFloor - floor);

		if (currentState == ElevatorStatus.State.STOPPED
				|| (currentFloor < floor && currentState == ElevatorStatus.State.GOING_UP)
				|| (currentFloor > floor && currentState == ElevatorStatus.State.GOING_DOWN)) {
			return absDistance;
		}

		if (currentFloor < floor && currentState == ElevatorStatus.State.GOING_DOWN) {
			return currentFloor + floor;
		}

		return (maxFloor - currentFloor) + (maxFloor - floor);
	}

	@Override
	public void addFloor(int floor) {
		Queue<Integer> targetQueue = upQueue;

		if (status.getFloor() > floor) {
			targetQueue = downQueue;
		}

		targetQueue.offer(floor);
	}

	@Override
	public boolean hasMore() {
		return !upQueue.isEmpty() || !downQueue.isEmpty();
	}

	@Override
	public void notify(ElevatorStatus status) {
		this.status = status;

		// If you reached the top floor and there is still work remaining in
		// upqueue
		// Move it to the down queue
		if (status.getFloor() >= maxFloor) {
			while (!upQueue.isEmpty()) {
				downQueue.offer(upQueue.poll());
			}
		}

		// If you reached the bottom floor and there is still work remaining in
		// down queue
		// Move it to the up queue
		if (status.getFloor() <= minFloor) {
			while (!downQueue.isEmpty()) {
				upQueue.offer(downQueue.poll());
			}
		}
	}
}
