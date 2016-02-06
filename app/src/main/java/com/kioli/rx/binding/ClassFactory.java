package com.kioli.rx.binding;

import com.kioli.rx.data.dao.MyDao;
import com.kioli.rx.data.manager.ConnectionManager;
import com.kioli.rx.data.manager.MyManager;

/**
 * Interface for the central unit handling managers and DAOs
 */
public interface ClassFactory {

	/**
	 * @return an implementation of the ConnectionManager
	 */
	ConnectionManager getConnectionManager();

	/**
	 * @return an implementation of the MyManager
	 */
	MyManager getMyManager();

	/**
	 * @return an implementation of the MyDao
	 */
	MyDao getMyDao();
}