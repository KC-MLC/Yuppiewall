package gabriel.yuppiewall.vaadin.application;

import java.io.Serializable;
import java.util.List;

public interface ApplicationService extends Serializable {

	public void registerApplication(
			@SuppressWarnings("rawtypes") Application application);

	public void unregisterApplication(
			@SuppressWarnings("rawtypes") Application module);

	@SuppressWarnings("rawtypes")
	public List<Application> getApplications();

}
