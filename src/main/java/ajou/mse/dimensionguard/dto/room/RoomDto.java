package ajou.mse.dimensionguard.dto.room;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class RoomDto {

    private Long id;
    private RoomStatus status;
    private Integer mapId;
    private LocalDateTime gameStartedAt;
    private List<PlayerDto> playerDtos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;

    public static RoomDto from(Room room) {
        return new RoomDto(
                room.getId(),
                room.getStatus(),
                room.getMapId(),
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
