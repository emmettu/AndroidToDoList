package com.example.todolist;

public class todoItem {
	
	private String name;
	
	private boolean status = false;
	
	public todoItem(String name) {
		this.name = name;
	}
	
	public boolean isDone() {
		return status;
	}
	public void setStatus(boolean newStatus) {
		status = newStatus;
	}
	
	public String getName() {
		return name;
	}
	public String toString() {
		return getName();
	}
}
