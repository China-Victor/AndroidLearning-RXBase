package com.kioli.rx.core.binding;

import com.kioli.rx.core.data.dao.UserDao;
import com.kioli.rx.core.data.manager.SchedulerManager;
import com.kioli.rx.core.data.manager.ServiceManager;
import com.kioli.rx.core.data.manager.UserManager;

public class ClassWiring {

	private static ClassWiring _instance;
	private ClassFactory _classFactory;
	private ServiceManager _serviceManager;
	private SchedulerManager _schedulerManager;
	private UserManager _userManager;
	private UserDao _userDao;

	private ClassWiring() {
	}

	public static ClassWiring getInstance() {
		if (_instance == null) {
			_instance = new ClassWiring();
		}
		return _instance;
	}

	/**
	 * Set the class factory
	 * Easy to switch to debug or mock mode
	 * In production this should be called only <b>once</b>!
	 *
	 * @param factory the factory providing all the managers and DAOs needed in this app
	 */
	public void setClassFactory(final ClassFactory factory) {
		_serviceManager = null;
		_schedulerManager = null;
		_userDao = null;
		_userManager = null;
		_classFactory = factory;
	}

	public ServiceManager getServiceManager() {
		if (_serviceManager == null) {
			_serviceManager = _classFactory.getServiceManager();
		}
		return _serviceManager;
	}

	public SchedulerManager getSchedulerManager() {
		if (_schedulerManager == null) {
			_schedulerManager = _classFactory.getSchedulerManager();
		}
		return _schedulerManager;
	}

	public UserDao getUserDao() {
		if (_userDao == null) {
			_userDao = _classFactory.getUserDao();
		}
		return _userDao;
	}

	public UserManager getUserManager() {
		if (_userManager == null) {
			_userManager = _classFactory.getUserManager();
		}
		return _userManager;
	}

}