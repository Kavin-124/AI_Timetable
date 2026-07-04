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
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity with static HTML
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/css/**", "/js/**").permitAll() // Allow everyone to see the login page
                        .anyRequest().authenticated() // Lock everything else
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Tell Spring to use our custom page!
                        .loginProcessingUrl("/login") // Where the form submits
                        .defaultSuccessUrl("/", true) // Where to go after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .permitAll()
                );

        return http.build();
    }
}