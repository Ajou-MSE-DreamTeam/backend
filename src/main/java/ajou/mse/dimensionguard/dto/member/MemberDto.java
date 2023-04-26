package ajou.mse.dimensionguard.dto.member;

import ajou.mse.dimensionguard.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberDto {

    private Integer id;
    private String accountId;
    private String password;
    private String name;

    public static MemberDto of(String accountId, String password, String name) {
        return new MemberDto(null, accountId, password, name);
    }

    public static MemberDto of(Integer id, String accountId, String password, String name) {
        return new MemberDto(id, accountId, password, name);
    }

    public static MemberDto from(Member entity) {
        return of(
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
