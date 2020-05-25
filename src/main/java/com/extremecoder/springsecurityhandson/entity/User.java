package com.extremecoder.springsecurityhandson.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@Data
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean isAccountNonExpired = true;

	@Column(nullable = false)
	private Boolean isAccountNonLocked = true;

	@Column(nullable = false)
	private Boolean isCredentialsNonExpired = true;

	@Column(nullable = false)
	private Boolean isEnabled = true;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
