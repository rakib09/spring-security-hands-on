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

#### In Memory Authorization
Now We will add Authorization- role wise access management.

Firstly We will add override method "protected void configure(HttpSecurity http)" in spring security configuration & Add user role wise url access like below:
```
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user").hasAnyRole("USER", "ADMIN")
				.antMatchers("/").permitAll()
				.and()
				.formLogin();
	}
```
Now We will add some method in homeController:
* "/" : For All User
* "/user" : For "USER" role
* "/admin" : For "ADMIN" role

Now these url can be accessed based on user role.

#### JDBC JPA Role Based Authentication

Firstly We will add a couple of dependency:
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```
Adding some properties in application properties file:
```
spring.datasource.url = jdbc:mysql://localhost:3306/app_db?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username = root
spring.datasource.password = P@ssw0rd

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```
Adding Entity class, Repository class, service class, ......
This project is available at "jpa-jdbc-role-based" branch.
