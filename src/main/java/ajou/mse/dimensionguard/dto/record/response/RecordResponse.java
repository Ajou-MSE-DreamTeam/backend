package ajou.mse.dimensionguard.dto.record.response;

import ajou.mse.dimensionguard.dto.record.RecordDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RecordResponse {

    @Schema(description = "PK of record", example = "6")
    private Long id;

    @Schema(description = "PK of room", example = "3")
    private Long roomId;

    @Schema(description = "Play time(sec)", example = "162")
    private Long playTimeSec;

    @Schema(description = "A record list of players who participated in the game.")
    private List<PlayerRecordResponse> playerRecords;

    public static RecordResponse from(RecordDto dto) {
        return new RecordResponse(
                dto.getId(),
                dto.getRoomId(),
                dto.getPlayTimeSec(),
                dto.getPlayerRecordDtos().stream()
                        .map(PlayerRecordResponse::from)
                        .toList()
        );
    }
}
