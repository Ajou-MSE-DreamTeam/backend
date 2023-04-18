package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.dto.auth.JwtTokenInfoDto;
import ajou.mse.dimensionguard.dto.auth.response.LoginResponse;
import ajou.mse.dimensionguard.dto.auth.response.TokenResponse;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.exception.auth.AccountIdNotFoundException;
import ajou.mse.dimensionguard.exception.auth.PasswordNotValidException;
import ajou.mse.dimensionguard.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(String accountId, String password) {
        MemberDto member = memberService.findOptionalDtoByAccountId(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(accountId));

        validatePassword(password, member);

        JwtTokenInfoDto accessToken = jwtTokenProvider.createAccessToken(member.getId());

        return LoginResponse.of(
                MemberResponse.from(member),
                TokenResponse.of(accessToken.token(), accessToken.expiresAt())
        );
    }

    private void validatePassword(String password, MemberDto member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordNotValidException();
        }
    }
}
