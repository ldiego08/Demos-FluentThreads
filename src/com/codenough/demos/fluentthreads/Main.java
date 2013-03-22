package com.codenough.demos.fluentthreads;

import static com.codenough.demos.fluentthreads.threading.Task.*;
import com.codenough.demos.fluentthreads.threading.*;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Creating task..");
		
		Task task = 
			createTaskFor(new Action() {
				@Override
				public void onExecute() {
					try {
						Thread.sleep(5000);
						System.out.println("Task internal action execution finished.");
					}
					catch(InterruptedException exception) {
						throw new Error("Thread sleep was interrupted.", exception);
					}
				}
			})
			.onErrorUse(new ErrorHandler() {
				@Override
				public void onErrorCaught(Exception exception) {
					System.out.println("An error ocurred while executing task internal action.");
				}
			})
			.thenExecute();
		
		System.out.println("Task created. Now running.");
		
		while(true) {
			Thread.sleep(1000);
			
			if (task.isRunning()) {
				System.out.println("Still waiting for task...");
			}
			else {
				break;
			}
		}
		
		System.out.println("Task execution completed.");
	}

}
