package io.getarrays.server.services;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.server.enities.Server;
import io.getarrays.server.enumeration.Status;
import io.getarrays.server.repositories.ServerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerImplementation implements ServerService{
	private final ServerRepo serverRepo;
	
	@Override
	public Server create(Server server) {
		log.info("Saving new Server {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		
		return serverRepo.save(server);
	}

	private String setServerImageUrl() {
		String[] imageServer = {"server1.png", "server2.png", "server3.png", "server4.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/images/" + imageServer[new Random().nextInt(4)]).toUriString();
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all ipAddress");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by id");
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating new Server {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting Server");
		serverRepo.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Server ping(String ipAddress) {
		try {
			log.info("Pinging server IP: {}", ipAddress);
			Server server = serverRepo.findByIpAddress(ipAddress);
			InetAddress inetAddress = InetAddress.getByName(ipAddress);
			server.setStatus(inetAddress.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
			return serverRepo.save(server);
		} catch (Exception e) {
			log.error("Pinging happended error : {}", e.getMessage());
		}
		return null;
		
	}

}
