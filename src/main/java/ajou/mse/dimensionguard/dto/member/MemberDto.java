package ajou.mse.dimensionguard.dto.member;

import ajou.mse.dimensionguard.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Getter
public class MemberDto {

    private Long id;
    private String accountId;
    private String password;
    private String name;

    public MemberDto(String accountId, String password, String name) {
        this.accountId = accountId;
        this.password = password;
        this.name = name;
    }

    public static MemberDto from(Member entity) {
        return new MemberDto(
                entity.getId(),
                entity.getAccountId(),
                entity.getPassword(),
                entity.getName()
        );
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.of(
                this.getAccountId(),
                passwordEncoder.encode(this.getPassword()),
                this.getName()
        );
    }
}
