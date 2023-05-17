package ajou.mse.dimensionguard.config;

import ajou.mse.dimensionguard.log.LogUtils;
import ajou.mse.dimensionguard.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            try {
                return Optional.ofNullable(SecurityContextHolder.getContext())
                        .map(SecurityContext::getAuthentication)
                        .filter(Authentication::isAuthenticated)
                        .map(Authentication::getPrincipal)
                        .map(UserPrincipal.class::cast)
                        .map(UserPrincipal::getMemberId);
            } catch (ClassCastException e) {
                // 로그인하지 않은 상황에서 Authentication.getPrincipal()을 사용하면 "anonymousUser"라는 String이 반환된다.
                // 이 경우, UserPrincipal로 casting 할 수 없어 ClassCastException이 발생한다.
                // 이 때문에 Optional.empty()를 반환하도록 구현.
                log.warn("[{}] Jpa auditing anonymous user warning. ex={}", LogUtils.getLogTraceId(), String.valueOf(e));
                return Optional.empty();
            }
        };
    }
}
