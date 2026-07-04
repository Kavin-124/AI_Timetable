package AI_Timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Allows our static HTML buttons to talk to the server safely
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // Locks EVERYTHING until logged in
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/index.html", true) // Sends you to the dashboard after logging in
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Sends you back to login screen after logging out
                        .permitAll()
                );
        return http.build();
    }
}