package io.getarrays.server.services;

import java.util.Collection;

import io.getarrays.server.enities.Server;

public interface ServerService {
	Server create(Server server);
	Collection<Server> list(int limit);
	Server get(Long id);
	Server update(Server server);
	Boolean delete(Long id);
	Server ping(String ipAddress);
}
