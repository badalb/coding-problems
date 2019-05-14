package com.test;

import java.util.Map;

public interface State {

	public Map<String, State> getAdjacentStates();

	public String getStateDesc();

	public void addTransit(Action action, State nextState);

	public void removeTransit(String targetStateDesc);
}
