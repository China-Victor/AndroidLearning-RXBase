package com.kioli.rx.binding;

import com.kioli.rx.data.dao.UserDao;
import com.kioli.rx.data.manager.SchedulerManager;
import com.kioli.rx.data.manager.ServiceManager;
import com.kioli.rx.data.manager.UserManager;

/**
 * Interface for the central unit handling managers and DAOs
 */
public interface ClassFactory {

	/**
	 * @return an implementation of the ServiceManager
	 */
	ServiceManager getServiceManager();

	/**
	 * @return an implementation of the SchedulerManager
	 */
	SchedulerManager getSchedulerManager();

	/**
	 * @return an implementation of the UserManager
	 */
	UserManager getUserManager();

	/**
	 * @return an implementation of the UserDao
	 */
	UserDao getUserDao();
}