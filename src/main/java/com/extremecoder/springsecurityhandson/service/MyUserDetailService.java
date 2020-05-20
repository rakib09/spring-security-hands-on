package com.extremecoder.springsecurityhandson.service;

import com.extremecoder.springsecurityhandson.entity.User;
import com.extremecoder.springsecurityhandson.repository.UserRepository;
import com.extremecoder.springsecurityhandson.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
public class MyUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public MyUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override @Transactional
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(s);
		if(ObjectUtils.isEmpty(user)) {
			user = userRepository.findByEmail(s);
		}
		if(ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("Could not find User");
		}
		return new MyUserDetails(user);
	}
}
