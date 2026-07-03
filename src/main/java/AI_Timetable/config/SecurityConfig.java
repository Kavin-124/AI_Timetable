package AI_Timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    // This tag tells IntelliJ to ignore its fake autowire error!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                // 1. Tell the guard to stop demanding the secret handshake (CSRF)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Keep the login requirement for everything else
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

                // 3. Keep the default login screen
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}