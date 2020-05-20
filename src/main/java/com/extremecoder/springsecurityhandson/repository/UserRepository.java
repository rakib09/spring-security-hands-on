package com.extremecoder.springsecurityhandson.repository;

import com.extremecoder.springsecurityhandson.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String userName);
	User findByEmail(String email);
}
