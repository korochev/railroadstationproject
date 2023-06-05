package ru.project.config.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.project.model.security.RegisterRequest;
import ru.project.service.security.AuthenticationService;

import static ru.project.model.security.user.Role.ADMIN;
import static ru.project.model.security.user.Role.USER;

@Configuration
@AllArgsConstructor
public class AdminAndUserInit {
    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            RegisterRequest admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.ru")
                    .password("admin")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            RegisterRequest user = RegisterRequest.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@mail.ru")
                    .password("user")
                    .role(USER)
                    .build();
            System.out.println("Manager token: " + service.register(user).getAccessToken());
        };
    }
}
