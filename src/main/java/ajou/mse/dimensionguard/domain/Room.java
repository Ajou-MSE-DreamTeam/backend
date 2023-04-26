package ajou.mse.dimensionguard.domain;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.player.Player;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;

    @Setter(AccessLevel.PRIVATE)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @OneToMany(mappedBy = "room")
    private List<Player> players = new LinkedList<>();

    public static Room of() {
        return of(null, RoomStatus.READY);
    }

    public static Room of(Integer id, RoomStatus status) {
        return Room.builder()
                .id(id)
                .status(status)
                .build();
    }

    public void init() {
        this.setStatus(RoomStatus.READY);
    }

    public void start() {
        this.setStatus(RoomStatus.IN_PROGRESS);
    }

    public void end() {
        this.setStatus(RoomStatus.DONE);
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Room(Integer id, RoomStatus status) {
        this.id = id;
        this.status = status;
    }
}
