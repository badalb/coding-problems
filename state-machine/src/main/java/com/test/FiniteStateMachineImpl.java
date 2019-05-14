package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiniteStateMachineImpl implements FiniteStateMachine {
    private State startState;
    private State endState;
    private State currentState;
    private ArrayList<State> allStates = new ArrayList<>();
    private HashMap<String, ArrayList<State>> mapForAllStates = new HashMap<>();

    public FiniteStateMachineImpl(){}
    
    @Override
    public void setStartState(State startState) {
        this.startState = startState;
        currentState = startState;
        allStates.add(startState);
        mapForAllStates.put(startState.getStateDesc(), new ArrayList<State>());
    }

    @Override
    public void setEndState(State endState) {
        this.endState = endState;
        allStates.add(endState);
        mapForAllStates.put(endState.getStateDesc(), new ArrayList<State>());
    }

    @Override
    public void addState(State startState, State newState, Action action) {
        allStates.add(newState);
        final String startStateDesc = startState.getStateDesc();
        final String newStateDesc = newState.getStateDesc();
        mapForAllStates.put(newStateDesc, new ArrayList<State>());
        ArrayList<State> adjacentStateList = null;
        if (mapForAllStates.containsKey(startStateDesc)) {
            adjacentStateList = mapForAllStates.get(startStateDesc);
            adjacentStateList.add(newState);
        } else {
            allStates.add(startState);
            adjacentStateList = new ArrayList<>();
            adjacentStateList.add(newState);
        }
        mapForAllStates.put(startStateDesc, adjacentStateList);

        // update mapping in startState
        for (State state : allStates) {
            boolean isStartState = state.getStateDesc().equals(startState.getStateDesc());
            if (isStartState) {
                startState.addTransit(action, newState);
            }
        }
    }

    @Override
    public void removeState(String targetStateDesc) {
        // validate state
        if (!mapForAllStates.containsKey(targetStateDesc)) {
            throw new RuntimeException("Don't have state: " + targetStateDesc);
        } else {
            // remove from mapping
            mapForAllStates.remove(targetStateDesc);
        }

        // update all state
        State targetState = null;
        for (State state : allStates) {
            if (state.getStateDesc().equals(targetStateDesc)) {
                targetState = state;
            } else {
                state.removeTransit(targetStateDesc);
            }
        }

        allStates.remove(targetState);

    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void transit(Action action) {
        if (currentState == null) {
            throw new RuntimeException("Please setup start state");
        }
        Map<String, State> localMapping = currentState.getAdjacentStates();
        if (localMapping.containsKey(action.toString())) {
            currentState = localMapping.get(action.toString());
        } else {
            throw new RuntimeException("No action start from current state");
        }
    }

    @Override
    public State getStartState() {
        return startState;
    }

    @Override
    public State getEndState() {
        return endState;
    }
}