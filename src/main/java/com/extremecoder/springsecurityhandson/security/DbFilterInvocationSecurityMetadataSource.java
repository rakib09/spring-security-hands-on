package com.extremecoder.springsecurityhandson.security;

import com.extremecoder.springsecurityhandson.common.Constants;
import com.extremecoder.springsecurityhandson.entity.Permission;
import com.extremecoder.springsecurityhandson.entity.Role;
import com.extremecoder.springsecurityhandson.entity.RolePermission;
import com.extremecoder.springsecurityhandson.repository.PermissionRepository;
import com.extremecoder.springsecurityhandson.repository.RolePermissionRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class DbFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final PermissionRepository permissionRepository;
	private final RolePermissionRepository rolePermissionRepository;

	public DbFilterInvocationSecurityMetadataSource(
			PermissionRepository permissionRepository,
			RolePermissionRepository rolePermissionRepository) {
		this.permissionRepository = permissionRepository;
		this.rolePermissionRepository = rolePermissionRepository;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if ("/login".equals(requestUrl) || requestUrl.contains("logout") || "/".equals(requestUrl) ) {
			return null;
		}
		List<Permission> permissionList = permissionRepository.findAll();
		for (Permission permission : permissionList) {
			if (requestUrl.equals(permission.getUrl())) {
				List<RolePermission> permissions = rolePermissionRepository.findAllByPermission(permission).orElse(null);
				List<String> roles = new LinkedList<>();
				if (!CollectionUtils.isEmpty(permissions)){
					Role role = permissions.get(0).getRole();
					roles.add(role.getName());
					return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
				}
			}
		}
		return SecurityConfig.createList(Constants.ROLE_LOGIN);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return false;
	}
}
