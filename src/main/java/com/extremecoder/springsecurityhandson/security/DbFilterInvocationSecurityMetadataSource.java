package com.extremecoder.springsecurityhandson.security;

import com.extremecoder.springsecurityhandson.common.Constants;
import com.extremecoder.springsecurityhandson.entity.Role;
import com.extremecoder.springsecurityhandson.entity.RolePermission;
import com.extremecoder.springsecurityhandson.repository.PermissionRepository;
import com.extremecoder.springsecurityhandson.repository.RolePermissionRepository;
import com.extremecoder.springsecurityhandson.repository.RoleRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DbFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final PermissionRepository permissionRepository;
	private final RolePermissionRepository rolePermissionRepository;
	private final RoleRepository roleRepository;
	private final AntPathMatcher antPathMatcher;

	public DbFilterInvocationSecurityMetadataSource(
			PermissionRepository permissionRepository,
			RolePermissionRepository rolePermissionRepository,
			RoleRepository roleRepository, AntPathMatcher antPathMatcher
	) {
		this.permissionRepository = permissionRepository;
		this.rolePermissionRepository = rolePermissionRepository;
		this.roleRepository = roleRepository;
		this.antPathMatcher = antPathMatcher;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if ("/login".equals(requestUrl) || requestUrl.contains("logout") || "/".equals(requestUrl) ) {
			return null;
		}
		String actualUrl = makeUrl(requestUrl);
		String method = ((FilterInvocation) object).getHttpRequest().getMethod();

		final List<RolePermission> rolePopedomDtos = rolePermissionRepository.findAll();
		final String[] matchedRoles = rolePopedomDtos.stream()
				.filter(
						rolePopedomDto ->
						antPathMatcher.match(rolePopedomDto.getPermission().getUrl(), actualUrl))
				.map(RolePermission::getRole).map(Role::getName)
				.collect(Collectors.toSet())
				.toArray(new String[0]);

		if (matchedRoles.length> 0) {
			return SecurityConfig.createList(matchedRoles);
		}
		return SecurityConfig.createList(Constants.ROLE_LOGIN);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return SecurityConfig.createList(
				roleRepository.findAll().stream()
						.map(Role::getName).collect(Collectors.toSet()).toArray( new String[]{})
		);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return FilterInvocation.class.isAssignableFrom(aClass);
	}

	private static String makeUrl(String url) {
		String[] split = url.split("/");
		if(split.length > 4) {
			StringBuilder newUrl = new StringBuilder();
			for(int i=1; i < 4; i++) {
				newUrl.append("/" + split[i]);
			}
			return newUrl.toString();
		}
		return url;
	}
}
