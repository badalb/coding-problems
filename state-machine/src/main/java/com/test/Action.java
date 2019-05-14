package com.test;

public class Action {
	
    private String actionName;

    public Action(String name) {
        actionName = name;
    }

    String getActionName() {
        return actionName;
    }

    @Override
    public String toString() {
        return actionName;
    }

}