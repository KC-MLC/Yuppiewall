package gabriel.yuppiewall.system.service;

import gabriel.yuppiewall.ds.domain.Server;

public interface ServerCacheService {

	Integer size();

	Boolean setIdentity(String requestID, Server identity);

	void clear();

}
