package com.user.mgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.entity.User;
import com.user.mgmt.exception.UserAlreadyExistException;
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

	public Page<User> getAllUsers(int page, int size) {
		return userRepository.findAllExceptAdmin(PageRequest.of(page, size));
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public boolean isUserExist(String userName) {
		
		Optional<User> optionalUser = userRepository.findById(userName);
		if(optionalUser.isPresent()) {
			return true;
		}
		return false;
	}

	

}
