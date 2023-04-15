package ajou.mse.dimensionguard.domain;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public static Room of() {
        return of(null, RoomStatus.READY);
    }

    public static Room of(Integer id, RoomStatus status) {
        return Room.builder()
                .id(id)
                .status(status)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Room(Integer id, RoomStatus status) {
        this.id = id;
        this.status = status;
    }
}
