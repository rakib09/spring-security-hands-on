package com.extremecoder.springsecurityhandson.startUp;

import com.extremecoder.springsecurityhandson.entity.Role;
import com.extremecoder.springsecurityhandson.entity.User;
import com.extremecoder.springsecurityhandson.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(
            UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void run(ApplicationArguments args) {
        userRepository.save(new User(
                "admin",
                bCryptPasswordEncoder.encode("admin"),
                new Role("ROLE_ADMIN"))
        );
        userRepository.save(new User(
                "user",
                bCryptPasswordEncoder.encode("user"),
                new Role("ROLE_USER"))
        );
    }
}
