package com.extremecoder.springsecurityhandson.security;

import com.extremecoder.springsecurityhandson.service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final MyUserDetailService userDetailsService;
	private final DbFilterInvocationSecurityMetadataSource dbFilterInvocationSecurityMetadataSource;
	private final UrlAccessDecisionManager urlAccessDecisionManager;

	public SecurityConfiguration(
			MyUserDetailService userDetailsService,
			DbFilterInvocationSecurityMetadataSource dbFilterInvocationSecurityMetadataSource,
			UrlAccessDecisionManager urlAccessDecisionManager) {
		this.userDetailsService = userDetailsService;
		this.dbFilterInvocationSecurityMetadataSource = dbFilterInvocationSecurityMetadataSource;
		this.urlAccessDecisionManager = urlAccessDecisionManager;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
				http.antMatcher("/**").authorizeRequests();

		registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O o) {
				o.setSecurityMetadataSource(dbFilterInvocationSecurityMetadataSource);
				o.setAccessDecisionManager(urlAccessDecisionManager);
				return o;
			}
		});

		registry.and().formLogin().permitAll();
		registry.anyRequest().authenticated();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
