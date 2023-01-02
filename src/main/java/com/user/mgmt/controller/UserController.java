package com.user.mgmt.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.entity.Role;
import com.user.mgmt.entity.User;
import com.user.mgmt.exception.UserNotValidException;
import com.user.mgmt.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	@PostMapping("/create")
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		Set<ConstraintViolation<User>> violations = validator.validate(user);

		if (!violations.isEmpty()) {
			throw new UserNotValidException(violations.toString());
		}

		System.out.println("SUCCESS -- ");
		
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

		System.out.println("password encoded ");
		
		
		// all users defaultly having user role
		Set<Role> defaultRoles = new HashSet<>();
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("This is Default role for the Users");
		defaultRoles.add(userRole);
		user.setRole(defaultRoles);

		System.out.println("user is going to create ");
		
		user = userService.addUser(user);
		
		System.out.println("created new user");
		
		return new ResponseEntity(user, HttpStatus.CREATED);
	}

	@GetMapping("/get")
	@PreAuthorize("hasRole('Admin')")
	public List<User> getUsers() {
		List<User> usersList = userService.getAllUsers();
		return usersList;
	}

	@GetMapping("/get/{userName}")
	public User getUserByUserName(@PathVariable String userName) {
		User user = userService.getUserByUserName(userName);
		return user;
	}

	@PutMapping("/update/{userName}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody User user) {

		User userObject = userService.getUserByUserName(userName);
		userObject.setUserFirstName(user.getUserFirstName());
		userObject.setUserLastName(user.getUserLastName());
		userObject = userService.addUser(userObject);
		return new ResponseEntity(userObject, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{userName}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String userName) {
		User user = userService.getUserByUserName(userName);
		userService.deleteUser(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@GetMapping({ "/forAdmin" })
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "This URL is only accessible to the admin";
	}

	@GetMapping({ "/forUser" })
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "This URL is only accessible to the user";
	}

}
