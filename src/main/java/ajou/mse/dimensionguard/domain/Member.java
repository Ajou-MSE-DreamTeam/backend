package ajou.mse.dimensionguard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String name;

    public static Member of(String accountId, String password, String name) {
        return of(null, accountId, password, name);
    }

    public static Member of(Integer id, String accountId, String password, String name) {
        return Member.builder()
                .id(id)
                .accountId(accountId)
                .password(password)
                .name(name)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Member(Integer id, String accountId, String password, String name) {
        this.id = id;
        this.accountId = accountId;
        this.password = password;
        this.name = name;
    }
}
