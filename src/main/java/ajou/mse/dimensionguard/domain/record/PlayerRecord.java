package ajou.mse.dimensionguard.domain.record;

import ajou.mse.dimensionguard.domain.BaseTimeEntity;
import ajou.mse.dimensionguard.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PlayerRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_record_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "record_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Record record;

    private Boolean isBoss;

    private Integer totalDamageDealt;

    private Integer totalDamageTaken;

    private Integer numOfSkillUsed;

    private Integer numOfSkillHit;

    public static PlayerRecord of(Member member, Record record, Boolean isBoss, Integer totalDamageDealt, Integer totalDamageTaken, Integer numOfSkillUsed, Integer numOfSkillHit) {
        return of(null, member, record, isBoss, totalDamageDealt, totalDamageTaken, numOfSkillUsed, numOfSkillHit, null, null);
    }

    public static PlayerRecord of(Long id, Member member, Record record, Boolean isBoss, Integer totalDamageDealt, Integer totalDamageTaken, Integer numOfSkillUsed, Integer numOfSkillHit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new PlayerRecord(id, member, record, isBoss, totalDamageDealt, totalDamageTaken, numOfSkillUsed, numOfSkillHit, createdAt, updatedAt);
    }

    public PlayerRecord(Long id, Member member, Record record, Boolean isBoss, Integer totalDamageDealt, Integer totalDamageTaken, Integer numOfSkillUsed, Integer numOfSkillHit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.member = member;
        this.record = record;
        this.isBoss = isBoss;
        this.totalDamageDealt = totalDamageDealt;
        this.totalDamageTaken = totalDamageTaken;
        this.numOfSkillUsed = numOfSkillUsed;
        this.numOfSkillHit = numOfSkillHit;
    }
}
