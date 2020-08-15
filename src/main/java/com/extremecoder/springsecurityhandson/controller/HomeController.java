package com.extremecoder.springsecurityhandson.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
public class HomeController {

	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome";
	}

	//  /path/12?var=value
	@GetMapping("/path/{pathId}")
	public String pathAndParam(@PathVariable("pathId") Long pathId, @RequestParam("var") String var) {
		return "Welcome " + pathId + " " + var;
	}

	@GetMapping("/admin")
	public String admin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Set<String> roles = authentication.getAuthorities().stream()
				.map(r -> r.getAuthority()).collect(Collectors.toSet());
		return "Hello Admin, your username: " + username + " And your roles: " + roles.toString();
	}

	@GetMapping("/user")
	public String user() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return "Hello, your username: " + username;
	}

}
