package com.kioli.rx.data.manager;

import rx.Scheduler;

/**
 * Interface for the scheduler manager
 */
public interface SchedulerManager {

	/**
	 * Returns a scheduler to which task will be given to be executed in BG threads
	 *
	 * @return scheduler running tasks
	 */
	Scheduler getSubscribeScheduler();
}
