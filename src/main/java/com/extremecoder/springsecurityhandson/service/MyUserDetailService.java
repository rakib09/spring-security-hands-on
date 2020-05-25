package com.extremecoder.springsecurityhandson.service;

import com.extremecoder.springsecurityhandson.entity.User;
import com.extremecoder.springsecurityhandson.entity.UserRole;
import com.extremecoder.springsecurityhandson.repository.UserRepository;
import com.extremecoder.springsecurityhandson.repository.UserRoleRepository;
import com.extremecoder.springsecurityhandson.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

	private final UserRoleRepository userRoleRepository;

	@Autowired
	public MyUserDetailService(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Override @Transactional
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		List<UserRole> userRoles = userRoleRepository.findAllByUser_Username(s).orElse(null);
		if(CollectionUtils.isEmpty(userRoles)) {
			throw new UsernameNotFoundException("Could not find User");
		}
		return new MyUserDetails(userRoles);
	}
}
