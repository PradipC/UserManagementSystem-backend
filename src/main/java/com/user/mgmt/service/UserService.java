package com.user.mgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.entity.User;
import com.user.mgmt.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User addUser(User user) {
		return userRepository.save(user);
	}

	public User getUserByUserName(String userName) {
       
		User user = userRepository.findById(userName).orElseThrow(() -> new RuntimeException("customer not found"));

		return user;
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
