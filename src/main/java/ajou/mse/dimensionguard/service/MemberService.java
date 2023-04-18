package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import ajou.mse.dimensionguard.dto.member.request.SignUpRequest;
import ajou.mse.dimensionguard.exception.member.AccountIdDuplicateException;
import ajou.mse.dimensionguard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto save(SignUpRequest request) {
        validateDuplicatedAccountId(request.getId());

        MemberDto memberDto = request.toDto();
        Member savedMember = memberRepository.save(memberDto.toEntity(passwordEncoder));
        return MemberDto.from(savedMember);
    }

    public boolean checkAccountIdExistence(String accountId) {
        return memberRepository.existsByAccountId(accountId);
    }

    private void validateDuplicatedAccountId(String accountId) {
        if (memberRepository.existsByAccountId(accountId)) {
            throw new AccountIdDuplicateException();
        }
    }
}
