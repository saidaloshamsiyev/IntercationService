package metube.com.intercationservice.config;

import metube.com.intercationservice.filter.CustomFilter;
import metube.com.intercationservice.filter.InteractionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //    .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/api/channels").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new CustomFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
