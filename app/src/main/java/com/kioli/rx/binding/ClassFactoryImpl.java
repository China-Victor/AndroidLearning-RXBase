package com.kioli.rx.binding;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kioli.rx.data.dao.MyDao;
import com.kioli.rx.data.dao.MyDaoImpl;
import com.kioli.rx.data.manager.ConnectionManager;
import com.kioli.rx.data.manager.ConnectionManagerImpl;
import com.kioli.rx.data.manager.MyManager;
import com.kioli.rx.data.manager.MyManagerImpl;
import com.kioli.rx.network.request.HttpRequestExecutorImpl;

public class ClassFactoryImpl implements ClassFactory {

	private Context _context;

	public ClassFactoryImpl(@NonNull Context context) {
		_context = context.getApplicationContext();
	}

	@Override
	public ConnectionManager getConnectionManager() {
		return new ConnectionManagerImpl(new HttpRequestExecutorImpl());
	}

	@Override
	public MyDao getMyDao() {
		return new MyDaoImpl(_context);
	}

	@Override
	public MyManager getMyManager() {
		return new MyManagerImpl();
	}
}
