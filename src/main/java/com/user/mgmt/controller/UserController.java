package com.user.mgmt.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	@GetMapping("/ping")
	public ResponseEntity<Boolean> ping(){
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	} 
	
	
	@GetMapping("/validat-name/{userName}")
	public ResponseEntity<Boolean> isUserExist(@PathVariable String userName){
		boolean userExist = userService.isUserExist(userName);
		return new ResponseEntity<Boolean>(userExist,HttpStatus.OK);
    } 
					
	
	
	
	@PostMapping("/create")
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		Set<ConstraintViolation<User>> violations = validator.validate(user);

		if (!violations.isEmpty()) {
			throw new UserNotValidException(violations.toString());
		}

		
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

		
		
		// all users defaultly having user role
		Set<Role> defaultRoles = new HashSet<>();
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("This is Default role for the Users");
		defaultRoles.add(userRole);
		user.setRole(defaultRoles);
		user = userService.addUser(user);
		return new ResponseEntity(user, HttpStatus.CREATED);
	}

	/*
	 * @GetMapping("/get/{page}/{size}")
	 * 
	 * @PreAuthorize("hasRole('Admin')") public Page<User>
	 * getUsers(@PathVariable(name = "page") int page,
	 * 
	 * @PathVariable(name = "size") int size) {
	 * 
	 * Page<User> usersList = userService.getAllUsers(page,size); return usersList;
	 * }
	 */

	
	@GetMapping("/get")
	@PreAuthorize("hasRole('Admin')")
	public List<User> getUsers() {
		List<User> usersList = userService.getUsers();
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
