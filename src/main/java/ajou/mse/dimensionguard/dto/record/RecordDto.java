package ajou.mse.dimensionguard.dto.record;

import ajou.mse.dimensionguard.domain.record.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RecordDto {

    private Long id;
    private Long roomId;
    private Long playTimeSec;
    private List<PlayerRecordDto> playerRecordDtos;

    public static RecordDto from(Record entity) {
        return new RecordDto(
                entity.getId(),
                entity.getRoomId(),
                entity.getPlayTimeSec(),
                entity.getPlayerRecords().stream()
                        .map(PlayerRecordDto::from)
                        .toList()
        );
    }
}
