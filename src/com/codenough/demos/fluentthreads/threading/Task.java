package com.codenough.demos.fluentthreads.threading;

public class Task {
	private Boolean isRunning;
	private ErrorHandler errorAction;
	private final Action action;
	
	public Task(Action action) {
		this.action = action;
		this.isRunning = false;
	}
	
	public Boolean isRunning() {
		return this.isRunning;
	}
	
	public Task onErrorUse(ErrorHandler errorAction) {
		this.errorAction = errorAction;
		
		return this;
	}
	
	public Task thenExecute() {
		Runnable runnableAction = new Runnable() {
			@Override
			public void run() {
				try {
					isRunning = true;
					action.onExecute();
					isRunning = false;
				} 
				catch(Exception exception) {
					errorAction.onErrorCaught(exception);
				}
			}
		};
		
		new Thread(runnableAction).start();
		
		return this;
	}
	
	public static Task createTaskFor(Action action) {
		return new Task(action);
	}
}
