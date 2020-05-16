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

