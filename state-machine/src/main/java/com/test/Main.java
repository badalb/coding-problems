package com.test;

public class Main {
	
	public static void main(String[] args) {
        System.out.println("Finite state machine!!!");
        State startState = new StateImpl("start");
        State endState = new StateImpl("end");
        
        FiniteStateMachine fsm = new FiniteStateMachineImpl();
        fsm.setStartState(startState);
        fsm.setEndState(endState);
        
        State middle1 = new StateImpl("middle1");
        middle1.addTransit(new Action("path1"), endState);
        fsm.addState(startState, middle1, new Action("path1"));
        System.out.println(fsm.getCurrentState().getStateDesc());
        
        fsm.transit(new Action(("path1")));
        System.out.println(fsm.getCurrentState().getStateDesc());
        
        fsm.addState(middle1, endState, new Action("path1-end"));
        fsm.transit(new Action(("path1-end")));
        
        System.out.println(fsm.getCurrentState().getStateDesc());
        fsm.addState(endState, middle1, new Action("path1-end"));
    }

}
