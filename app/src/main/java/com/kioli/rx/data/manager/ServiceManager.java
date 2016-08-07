package com.kioli.rx.data.manager;

import com.kioli.rx.service.ServiceDemandware;

/**
 * Interface for the central unit handling services
 */
public interface ServiceManager {

	/**
	 * Returns the demandware service
	 *
	 * @return service to use demandware endpoints
	 */
	ServiceDemandware getServiceDemandware();
}
