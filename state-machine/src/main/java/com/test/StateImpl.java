package com.test;

import java.util.HashMap;
import java.util.Map;

public class StateImpl implements State {
	
    private HashMap<String, State> mapping = new HashMap<>();
    
    private String stateName;

    public StateImpl(String name) {
        stateName = name;
    }

    @Override
    public Map<String, State> getAdjacentStates() {
        return mapping;
    }

    @Override
    public String getStateDesc() {
        return stateName;
    }

    @Override
    public void addTransit(Action action, State state) {
        mapping.put(action.toString(), state);
    }

    @Override
    public void removeTransit(String targetStateDesc) {
        String targetAction = null;
        for (Map.Entry<String, State> entry : mapping.entrySet()) {
            State state = entry.getValue();
            if (state.getStateDesc().equals(targetStateDesc)) {
                targetAction = entry.getKey();
            }
        }
        mapping.remove(targetAction);
    }

}