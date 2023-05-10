package ajou.mse.dimensionguard.domain;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.player.Player;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Setter
    private LocalDateTime gameStartedAt;

    @OneToMany(mappedBy = "room")
    private List<Player> players = new LinkedList<>();

    public static Room of() {
        return of(null, RoomStatus.READY, null);
    }

    public static Room of(Integer id, RoomStatus status, LocalDateTime gameStartedAt) {
        return Room.builder()
                .id(id)
                .status(status)
                .gameStartedAt(gameStartedAt)
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
    private Room(Integer id, RoomStatus status, LocalDateTime gameStartedAt) {
        this.id = id;
        this.status = status;
        this.gameStartedAt = gameStartedAt;
    }
}
