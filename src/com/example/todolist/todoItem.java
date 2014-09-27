package com.example.todolist;

import java.io.Serializable;

public class todoItem implements Serializable {
	
	/**
	 * Serialized
	 */
	private static final long serialVersionUID = 3004098601522293129L;

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
