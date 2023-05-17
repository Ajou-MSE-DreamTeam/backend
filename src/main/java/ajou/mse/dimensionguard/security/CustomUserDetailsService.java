package ajou.mse.dimensionguard.security;

import ajou.mse.dimensionguard.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class CustomUserDetailsService {

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return username -> UserPrincipal.of(memberService.findDtoById(Long.parseLong(username)));
    }
}
