package com.kioli.rx.data.manager.implementation;

import com.kioli.rx.data.manager.SchedulerManager;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class SchedulerManagerImpl implements SchedulerManager {

	private Scheduler _subscribeScheduler;

	@Override
	public Scheduler getSubscribeScheduler() {
		if (_subscribeScheduler == null) {
			_subscribeScheduler = Schedulers.io();
		}
		return _subscribeScheduler;
	}
}
