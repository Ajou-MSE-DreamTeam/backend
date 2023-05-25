package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.constant.GameResult;
import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.record.PlayerRecord;
import ajou.mse.dimensionguard.domain.record.Record;
import ajou.mse.dimensionguard.dto.record.RecordDto;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.exception.record.RecordNotFoundByRoomIdException;
import ajou.mse.dimensionguard.repository.record.PlayerRecordRepository;
import ajou.mse.dimensionguard.repository.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

    private final MemberService memberService;
    private final RecordRepository recordRepository;
    private final PlayerRecordRepository playerRecordRepository;

    @Transactional
    public RecordDto record(RoomDto roomDto, GameResult gameResult) {
        Duration duration = Duration.between(roomDto.getGameStartedAt(), LocalDateTime.now());
        Record record = Record.of(roomDto.getId(), gameResult, duration.toSeconds());
        recordRepository.save(record);

        List<PlayerRecord> playerRecords = roomDto.getPlayerDtos().stream()
                .map(dto -> {
                    Member member = memberService.findById(dto.getMemberDto().getId());
                    return PlayerRecord.of(
                            member,
                            record,
                            dto.getIsBoss(),
                            dto.getTotalDamageDealt(),
                            dto.getTotalDamageTaken(),
                            dto.getNumOfSkillUsed(),
                            dto.getNumOfSkillHit()
                    );
                })
                .toList();
        playerRecordRepository.saveAll(playerRecords);
        record.getPlayerRecords().addAll(playerRecords);

        return RecordDto.from(record);
    }
}
