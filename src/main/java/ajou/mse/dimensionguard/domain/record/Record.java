package ajou.mse.dimensionguard.domain.record;

import ajou.mse.dimensionguard.constant.GameResult;
import ajou.mse.dimensionguard.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GameResult result;

    @Column(nullable = false)
    private Long playTimeSec;

    @OneToMany(mappedBy = "record")
    private List<PlayerRecord> playerRecords = new LinkedList<>();

    public static Record of(Long roomId, GameResult result, Long playTimeSec) {
        return of(null, roomId, result, playTimeSec, null, null);
    }

    public static Record of(Long id, Long roomId, GameResult result, Long playTimeSec, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Record(id, roomId, result, playTimeSec, createdAt, updatedAt);
    }

    private Record(Long id, Long roomId, GameResult result, Long playTimeSec, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.roomId = roomId;
        this.result = result;
        this.playTimeSec = playTimeSec;
    }
}
