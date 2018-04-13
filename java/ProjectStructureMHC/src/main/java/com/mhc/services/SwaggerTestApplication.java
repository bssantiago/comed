package com.mhc.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerTestApplication extends Application {

	public SwaggerTestApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setTitle("Comde Documentation");
		beanConfig.setBasePath("/swagger");
		beanConfig.setResourcePackage("com.mhc.rest.privated");
		beanConfig.setScan(true);
	}
	
	@Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();

        set.add(Resource.class);

        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return set;
    }
}