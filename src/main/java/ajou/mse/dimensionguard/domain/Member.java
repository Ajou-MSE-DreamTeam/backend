package ajou.mse.dimensionguard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
        return of(null, accountId, password, name, null, null);
    }

    public static Member of(Integer id, String accountId, String password, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Member(id, accountId, password, name, createdAt, updatedAt);
    }

    private Member(Integer id, String accountId, String password, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.accountId = accountId;
        this.password = password;
        this.name = name;
    }
}
