package ajou.mse.dimensionguard.dto.room;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomDto {

    private Integer id;
    private RoomStatus status;
    private LocalDateTime gameStartedAt;
    private List<PlayerDto> playerDtos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;

    public static RoomDto of(Integer id, RoomStatus status, LocalDateTime gameStartedAt, List<PlayerDto> playerDtos, LocalDateTime createdAt, LocalDateTime updatedAt, Integer createdBy, Integer updatedBy) {
        return new RoomDto(id, status, gameStartedAt, playerDtos, createdAt, updatedAt, createdBy, updatedBy);
    }

    public static RoomDto from(Room room) {
        return of(
                room.getId(),
                room.getStatus(),
                room.getGameStartedAt(),
                room.getPlayers().stream()
                        .map(PlayerDto::from)
                        .toList(),
                room.getCreatedAt(),
                room.getUpdatedAt(),
                room.getCreatedBy(),
                room.getUpdatedBy()
        );
    }
}
