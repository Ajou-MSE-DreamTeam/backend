package ajou.mse.dimensionguard.domain.player;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

import static ajou.mse.dimensionguard.constant.ConstantUtil.BOSS_MAX_ENERGY;
import static ajou.mse.dimensionguard.constant.ConstantUtil.BOSS_MAX_HP;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("boss")
@Entity
public class Boss extends Player {

    @Column(nullable = false)
    private Integer numOfSkillUsed;

    @Column(nullable = false)
    private Integer numOfSkillHit;

    public static Boss of(Member member, Room room) {
        return of(null, member, room, false, BOSS_MAX_HP, BOSS_MAX_ENERGY, 0, 0, null, null);
    }

    public static Boss of(Long id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Integer numOfSkillUsed, Integer numOfSkillHit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Boss(id, member, room, isReady, hp, energy, numOfSkillUsed, numOfSkillHit, createdAt, updatedAt);
    }

    private Boss(Long id, Member member, Room room, Boolean isReady, Integer hp, Integer energy, Integer numOfSkillUsed, Integer numOfSkillHit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, member, room, isReady, hp, energy, createdAt, updatedAt);
        this.numOfSkillUsed = numOfSkillUsed;
        this.numOfSkillHit = numOfSkillHit;
    }

    public void decreaseHp(Integer damageDealt) {
        this.setHp(this.getHp() - damageDealt);
    }

    public void increaseNumOfSkillUsed() {
        this.numOfSkillUsed++;
    }

    public void increaseNumOfSkillHit() {
        this.numOfSkillHit++;
    }
}
