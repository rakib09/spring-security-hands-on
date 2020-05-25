package com.extremecoder.springsecurityhandson.repository;

import com.extremecoder.springsecurityhandson.entity.Role;
import com.extremecoder.springsecurityhandson.entity.User;
import com.extremecoder.springsecurityhandson.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	Optional<UserRole> findByUserAndRole(User user, Role role);

	Optional<List<UserRole>> findAllByUser_Username(String username);
}
