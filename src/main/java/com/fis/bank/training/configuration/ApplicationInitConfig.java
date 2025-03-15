package com.fis.bank.training.configuration;

import java.util.HashSet;
import java.util.Set;

import com.fis.bank.training.constant.PredefinedRole;
import com.fis.bank.training.model.Role;
import com.fis.bank.training.model.User;
import com.fis.bank.training.repository.RoleRepository;
import com.fis.bank.training.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;


@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_NAME = "master";

    @NonFinal
    static final String ADMIN_PASSWORD = "913020";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name = "driver-class-name",
            havingValue = "org.postgres.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application");
        return args -> {
            // Check if the admin user already exists
            if (userRepository.findByUsername(ADMIN_NAME).isEmpty()) {
                // Create and save roles
                Role userRole = Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build();
                Role adminRole = Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build();
                roleRepository.save(userRole);
                adminRole = roleRepository.save(adminRole);

                // Create and save the admin user with adminRole
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User user = User.builder()
                        .username(ADMIN_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);

                log.warn("Admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
