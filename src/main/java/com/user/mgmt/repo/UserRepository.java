package com.user.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.mgmt.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,String>{

	User findByUserName(String userName);
	
	
}
