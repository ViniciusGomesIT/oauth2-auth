package br.com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.entity.User;
import br.com.api.reposiroty.UserRepository;

@RestController
@RequestMapping(value = "/api/user")
public class UserControllerImpl {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(this.repository.findAll());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getAllUsers(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok().body(this.repository.findById(id).get());
	}
}
