package internationalmoneytransferapp.Backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import internationalmoneytransferapp.Backend.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTAuthEntryPoint authEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {

        return httpSecurity
                .csrf((csrf) -> csrf.disable())
                // .cors((cors) -> cors.disable())
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPoint))
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers("/api/transactions/**")
                                // .permitAll()
                                .hasRole("USER")
                                .requestMatchers("/api/beneficiaries/**")
                                .hasRole("USER")
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                        .deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true).logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                // .httpBasic(Customizer.withDefaults())
                .build();
    }

    // @Bean
    // public UserDetailsService users() {
    // PasswordEncoder encoder =
    // PasswordEncoderFactories.createDelegatingPasswordEncoder();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password(encoder.encode("password"))
    // .roles("ADMIN")
    // .build();

    // UserDetails user = User.builder()
    // .username("user")
    // .password(encoder.encode("password"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(admin, user);
    // }

    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
