package com.extremecoder.springsecurityhandson.security;

import com.extremecoder.springsecurityhandson.entity.Role;
import com.extremecoder.springsecurityhandson.entity.User;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class MyUserDetails implements UserDetails {

	private User user;

	public MyUserDetails(User user) {
		this.user = user;
	}

	@Transactional
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roleList = user.getRoles();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (!CollectionUtils.isEmpty(roleList)) {
			for (Role role : roleList) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
				grantList.add(authority);
			}
		}
		return grantList;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getIsAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getIsCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.getIsEnabled();
	}
}
