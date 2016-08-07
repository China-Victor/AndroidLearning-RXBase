package com.kioli.rx.data.manager.implementation;

import com.kioli.rx.data.manager.ServiceManager;
import com.kioli.rx.service.ServiceDemandware;

public class ServiceManagerImpl implements ServiceManager {

	private ServiceDemandware _demandwareService;

	@Override
	public ServiceDemandware getServiceDemandware() {
		if (_demandwareService == null) {
			_demandwareService = ServiceDemandware.Factory.create();
		}
		return _demandwareService;
	}
}
