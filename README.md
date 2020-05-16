# spring-security-hands-on
spring-security-hands-on

#### Spring Security Adding
After Create a Spring boot Application, We will add the spring security dependency in pom.xml file:
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>2.3.0.RELEASE</version>
        </dependency>
```
In Spring boot project, After adding Spring Security, security will start working without any configuration.

* An user named "user" will be created & password will be printed in log
* All url/servlet will be required authentication
* If Developer added user password for any user in application.properties file then spring security will be created that one & skip the default user creation.
```
spring.security.user.name=admin
spring.security.user.password=admin   
```
In this Application We are using 8082 port for embedded apache

Got to http://localhost:8082/

The default behaviour's source code is under [default-spring-security](https://github.com/rakib09/spring-security-hands-on/tree/default-spring-security) branch.

#### In- Memory Authentication
If we want to configure security by using Configuration Java Class, We will add a class "SecurityConfiguration" & extends "WebSecurityConfigurerAdapter" & Add "@EnableWebSecurity" annotation in the class.
After That we will add "protected void configure(AuthenticationManagerBuilder auth)" override method in this class & implement inMemoryAuthentication configuration like below:
```
auth.inMemoryAuthentication()
				.withUser("admin")
				.password("admin")
				.roles("ADMIN")
				.and()
				.withUser("user")
				.password("user")
				.roles("USER");
```
Spring Security need a PasswordEncoder bean for encoding password. Hence, We will add a bean of PasswordEncoder.
```
@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
```  
Spring security will create two user (admin, user) with the given password & role at the startup of the project.

Note That: Spring security have a logout endpoint. We can logout by using "/logout" url.
