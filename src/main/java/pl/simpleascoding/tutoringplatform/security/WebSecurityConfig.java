package pl.simpleascoding.tutoringplatform.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.simpleascoding.tutoringplatform.security.jwt.JwtAuthenticationFilter;
import pl.simpleascoding.tutoringplatform.user.RoleType;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors();

        httpSecurity.logout().invalidateHttpSession(true).clearAuthentication(true).permitAll();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeRequests().antMatchers("/testAdmin").hasAnyAuthority(RoleType.ADMIN.toString());
        httpSecurity.authorizeRequests().antMatchers("/testUser").hasAnyAuthority(RoleType.USER.toString());
        httpSecurity.authorizeRequests().antMatchers("/api/v1/users/change-password").hasAnyAuthority(RoleType.USER.toString());
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/reviews").authenticated();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/reviews/{id}").authenticated();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/reviews/{id}").authenticated();
        httpSecurity.authorizeRequests().anyRequest().permitAll();
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
