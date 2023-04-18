package ajou.mse.dimensionguard.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private static final String BASE_URL = "/api";
    private static final String[] AUTH_WHITE_LIST = {
            "/members",
            "/members/nickname/existence",
            "/auth/login/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                // JWT 기반 인증이기 때문에 session 사용 X
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .mvcMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll();
                    Arrays.stream(AUTH_WHITE_LIST)
                            .forEach(authWithListElem ->
                                    auth.mvcMatchers(BASE_URL + authWithListElem).permitAll());
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
