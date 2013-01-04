package com.sdsi.bmrc.client.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@SuppressWarnings("serial")
@Service("moduleServiceImpl")
@Scope("prototype")
public class ApplicationServiceImpl implements ApplicationService {

	@SuppressWarnings("rawtypes")
	private ArrayList<Application> apps = new ArrayList<Application>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerApplication(
			@SuppressWarnings("rawtypes") Application application) {
		apps.add(application);

	}

	@Override
	@SuppressWarnings("unchecked")
	public void unregisterApplication(
			@SuppressWarnings("rawtypes") Application module) {
		apps.remove(module);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Application> getApplications() {
		return Collections.unmodifiableList(apps);
	}

}