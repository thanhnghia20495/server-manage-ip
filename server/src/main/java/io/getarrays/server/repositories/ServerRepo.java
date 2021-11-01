package io.getarrays.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.server.enities.Server;

public interface ServerRepo extends JpaRepository<Server, Long>{
	Server findByIpAddress(String ipAddress);
}
