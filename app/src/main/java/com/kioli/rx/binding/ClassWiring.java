package com.kioli.rx.binding;

import com.kioli.rx.data.dao.MyDao;
import com.kioli.rx.data.manager.ConnectionManager;
import com.kioli.rx.data.manager.MyManager;

public final class ClassWiring {

	private static ClassFactory _classFactory;
	private static ConnectionManager _connectionManager;
	private static MyManager _myManager;
	private static MyDao _myDao;

	private ClassWiring() {
	}

	/**
	 * Set the class factory
	 * Easy to switch to debug or mock mode
	 * In production this should be called only <b>once</b>!
	 *
	 * @param factory the factory providing all the managers and DAOs needed in this app
	 */
	public static void setClassFactory(final ClassFactory factory) {
		_connectionManager = null;
		_myDao = null;
		_myManager = null;
		_classFactory = factory;
	}

	public static ConnectionManager getConnectionManager() {
		if (_connectionManager == null) {
			_connectionManager = _classFactory.getConnectionManager();
		}
		return _connectionManager;
	}

	public static MyDao getMyDao() {
		if (_myDao == null) {
			_myDao = _classFactory.getMyDao();
		}
		return _myDao;
	}

	public static MyManager getMyManager() {
		if (_myManager == null) {
			_myManager = _classFactory.getMyManager();
		}
		return _myManager;
	}

}