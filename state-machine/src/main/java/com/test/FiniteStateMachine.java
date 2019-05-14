package com.test;

public interface FiniteStateMachine {
	
	void setStartState(State startState);

	void setEndState(State endState);

	void addState(State startState, State newState, Action action);

	void removeState(String targetStateDesc);

	State getCurrentState();

	State getStartState();

	State getEndState();

	void transit(Action action);
}
