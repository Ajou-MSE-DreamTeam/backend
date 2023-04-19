package ajou.mse.dimensionguard.domain.player;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public abstract class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Integer id;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false)
    private Boolean isReady;

    @Column(nullable = false)
    private Integer hp;

    @Column(nullable = false)
    private Integer energy;

    public void setReady() {
        this.setIsReady(true);
    }

    public void setNotReady() {
        this.setIsReady(false);
    }

    protected Player(Integer id, Member member, Room room, Boolean isReady, Integer hp, Integer energy) {
        this.id = id;
        this.member = member;
        this.room = room;
        this.isReady = isReady;
        this.hp = hp;
        this.energy = energy;
    }
}
