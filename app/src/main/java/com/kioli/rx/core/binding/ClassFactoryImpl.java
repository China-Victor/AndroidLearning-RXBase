package com.kioli.rx.core.binding;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kioli.rx.core.data.dao.UserDao;
import com.kioli.rx.core.data.dao.UserDaoImpl;
import com.kioli.rx.core.data.manager.SchedulerManager;
import com.kioli.rx.core.data.manager.ServiceManager;
import com.kioli.rx.core.data.manager.UserManager;
import com.kioli.rx.core.data.manager.implementation.SchedulerManagerImpl;
import com.kioli.rx.core.data.manager.implementation.ServiceManagerImpl;
import com.kioli.rx.core.data.manager.implementation.UserManagerImpl;

public class ClassFactoryImpl implements ClassFactory {

	private Context _context;

	public ClassFactoryImpl(@NonNull Context context) {
		_context = context.getApplicationContext();
	}

	@Override
	public ServiceManager getServiceManager() {
		return new ServiceManagerImpl();
	}

	@Override
	public SchedulerManager getSchedulerManager() {
		return new SchedulerManagerImpl();
	}

	@Override
	public UserDao getUserDao() {
		return new UserDaoImpl();
	}

	@Override
	public UserManager getUserManager() {
		return new UserManagerImpl();
	}
}
