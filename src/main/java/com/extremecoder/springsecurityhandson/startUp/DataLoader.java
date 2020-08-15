package com.extremecoder.springsecurityhandson.startUp;

import com.extremecoder.springsecurityhandson.entity.*;
import com.extremecoder.springsecurityhandson.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void run(ApplicationArguments args) {
        User user = userRepository.findByUsername("user").orElse(
                userRepository.save(new User("user",
                        bCryptPasswordEncoder.encode("user")))
        );
        User admin = userRepository.findByUsername("admin").orElse(
                userRepository.save(new User("admin",
                        bCryptPasswordEncoder.encode("admin")))
        );
        Role roleOfUser = roleRepository.findByName("ROLE_USER").orElse(roleRepository.save(new Role("ROLE_USER")));
        Role roleOfAdmin = roleRepository.findByName("ROLE_ADMIN").orElse(roleRepository.save(new Role("ROLE_ADMIN")));
        userRoleRepository.findByUserAndRole(user, roleOfUser).orElse(
                userRoleRepository.save(new UserRole(user, roleOfUser)));

        userRoleRepository.findByUserAndRole(admin, roleOfAdmin).orElse(
                userRoleRepository.save(new UserRole(admin, roleOfAdmin)));


        Permission adminPermission = permissionRepository.findByUrl("/api/home/admin")
                .orElse(permissionRepository.save(new Permission("/api/home/admin")));

        Permission pathPermission = permissionRepository.findByUrl("/api/home/path")
                .orElse(permissionRepository.save(new Permission("/api/home/path")));

        Permission userPermission = permissionRepository.findByUrl("/api/home/user")
                .orElse(permissionRepository.save(new Permission("/api/home/user")));

        rolePermissionRepository.findByPermission(adminPermission).orElse(
                rolePermissionRepository.save(
                        new RolePermission(roleOfAdmin, adminPermission)
                )
        );
        rolePermissionRepository.findByPermission(adminPermission).orElse(
                rolePermissionRepository.save(
                        new RolePermission(roleOfAdmin, pathPermission)
                )
        );

    }
}
