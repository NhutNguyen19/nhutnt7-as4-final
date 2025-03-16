package com.fis.bank.training.configuration;

import java.util.HashSet;
import java.util.Set;

import com.fis.bank.training.constant.PredefinedRole;
import com.fis.bank.training.model.Permission;
import com.fis.bank.training.model.Role;
import com.fis.bank.training.model.User;
import com.fis.bank.training.repository.PermissionRepository;
import com.fis.bank.training.repository.RoleRepository;
import com.fis.bank.training.repository.UserRepository;
import org.camunda.bpm.engine.impl.identity.Account;
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
    static final String ADMIN_NAME = "main";

    @NonFinal
    static final String ADMIN_PASSWORD = "913020";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name = "driver-class-name",
            havingValue = "org.postgresql.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        log.info("Initializing application");

        return args -> {
            // Tạo quyền nếu chưa có
            Permission readUser = createPermissionIfNotExists(permissionRepository, "READ_USER", "Quyền đọc thông tin người dùng");
            Permission writeUser = createPermissionIfNotExists(permissionRepository, "WRITE_USER", "Quyền chỉnh sửa thông tin người dùng");
            Permission deleteUser = createPermissionIfNotExists(permissionRepository, "DELETE_USER", "Quyền xóa người dùng");
            Permission manageRole = createPermissionIfNotExists(permissionRepository, "MANAGE_ROLE", "Quyền quản lý vai trò");

            // Tạo Role nếu chưa có
            Role userRole = roleRepository.findByName(PredefinedRole.USER_ROLE)
                    .orElseGet(() -> {
                        Role role = Role.builder()
                                .name(PredefinedRole.USER_ROLE)
                                .description("User role")
                                .permissions(Set.of(readUser))  // User chỉ có quyền READ_USER
                                .build();
                        return roleRepository.save(role);
                    });

            Role adminRole = roleRepository.findByName(PredefinedRole.ADMIN_ROLE)
                    .orElseGet(() -> {
                        Role role = Role.builder()
                                .name(PredefinedRole.ADMIN_ROLE)
                                .description("Admin role")
                                .permissions(Set.of(readUser, writeUser, deleteUser, manageRole)) // Admin có toàn bộ quyền
                                .build();
                        return roleRepository.save(role);
                    });

            // Tạo admin user nếu chưa có
            if (userRepository.findByUsername(ADMIN_NAME).isEmpty()) {
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

    private Permission createPermissionIfNotExists(PermissionRepository permissionRepository, String name, String description) {
        return permissionRepository.findByName(name).orElseGet(() -> {
            Permission permission = Permission.builder()
                    .name(name)
                    .description(description)
                    .build();
            return permissionRepository.save(permission);
        });
    }

}
