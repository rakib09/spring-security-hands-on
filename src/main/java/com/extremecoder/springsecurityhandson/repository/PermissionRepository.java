package com.extremecoder.springsecurityhandson.repository;

import com.extremecoder.springsecurityhandson.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Optional<Permission> findByUrl(String url);
}
