package com.extremecoder.springsecurityhandson.repository;

import com.extremecoder.springsecurityhandson.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
