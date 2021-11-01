package io.getarrays.server.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.server.enities.Response;
import io.getarrays.server.enities.Server;
import io.getarrays.server.enumeration.Status;
import io.getarrays.server.services.ServerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
	private final ServerService serverService;

	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		Map<String, Collection<Server>> map = new HashMap<String, Collection<Server>>();
		map.put("servers", serverService.list(30));
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message("Servers retrieved")
					.status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) {
		Server server = serverService.ping(ipAddress);
		Map<String, Server> map = new HashMap<String, Server>();
		map.put("server", server);
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
					.status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Response> createServer(@RequestBody @Valid Server server) {
		Map<String, Server> map = new HashMap<String, Server>();
		map.put("server", serverService.create(server));
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message("Server created")
					.status(HttpStatus.CREATED)
					.statusCode(HttpStatus.CREATED.value()).build());
	}
	
	@PostMapping("/update")
	public ResponseEntity<Response> updateServer(@RequestBody @Valid Server server) {
		Map<String, Server> map = new HashMap<String, Server>();
		map.put("server", serverService.update(server));
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message("Server updated")
					.status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
		Map<String, Server> map = new HashMap<String, Server>();
		map.put("server", serverService.get(id));
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message("Server retrieved")
					.status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("deleted", serverService.delete(id));
		return ResponseEntity.ok(
				Response.builder()
					.timeStamp(LocalDateTime.now())
					.data(map)
					.message("Server deleted")
					.status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
	}
	
	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
	}

}
