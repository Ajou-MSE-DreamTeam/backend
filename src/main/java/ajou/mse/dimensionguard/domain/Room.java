package ajou.mse.dimensionguard.domain;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.player.Player;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Setter
    private LocalDateTime gameStartedAt;

    @OneToMany(mappedBy = "room")
    private List<Player> players = new LinkedList<>();

    public static Room of() {
        return of(null, RoomStatus.READY, null, null, null, null, null, null);
    }

    public static Room of(Long id, RoomStatus status, LocalDateTime gameStartedAt, List<Player> players, LocalDateTime createdAt, LocalDateTime updatedAt, Integer createdBy, Integer updatedBy) {
        return new Room(id, status, gameStartedAt, players, createdAt, updatedAt, createdBy, updatedBy);
    }

    private Room(Long id, RoomStatus status, LocalDateTime gameStartedAt, List<Player> players, LocalDateTime createdAt, LocalDateTime updatedAt, Integer createdBy, Integer updatedBy) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.status = status;
        this.gameStartedAt = gameStartedAt;
        this.players = players;
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
}
