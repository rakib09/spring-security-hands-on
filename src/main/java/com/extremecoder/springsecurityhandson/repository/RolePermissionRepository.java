package com.extremecoder.springsecurityhandson.repository;

import com.extremecoder.springsecurityhandson.entity.Permission;
import com.extremecoder.springsecurityhandson.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

	Optional<RolePermission> findByPermission(Permission permission);
	Optional<List<RolePermission>> findAllByPermission(Permission permission);
}
