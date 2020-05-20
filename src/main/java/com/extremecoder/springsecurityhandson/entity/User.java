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

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_role",
			joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }
			)
	private Set<Role> roles = new HashSet<Role>();

	public User(String username, String password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.roles = Collections.singleton(role);
	}
}
