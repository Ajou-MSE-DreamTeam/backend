package ajou.mse.dimensionguard.domain.player;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("boss")
@Entity
public class Boss extends Player {

    public static final int BOSS_MAX_HP = 100;
    public static final int BOSS_MAX_ENERGY = 100;

    public static Boss of(Member member, Room room) {
        return of(null, member, room, false, BOSS_MAX_HP, BOSS_MAX_ENERGY);
    }

    public static Boss of(Integer id, Member member, Room room, Boolean isReady, Integer hp, Integer energy) {
        return Boss.builder()
                .id(id)
                .member(member)
                .room(room)
                .isReady(isReady)
                .hp(hp)
                .energy(energy)
                .build();
    }

    public void decreaseHp(Integer damageDealt) {
        this.setHp(this.getHp() - damageDealt);
    }

    @Builder(access = AccessLevel.PROTECTED)
    private Boss(Integer id, Member member, Room room, Boolean isReady, Integer hp, Integer energy) {
        super(id, member, room, isReady, hp, energy);
    }
}
