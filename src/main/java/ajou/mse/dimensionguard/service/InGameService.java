package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.dto.in_game.request.PlayerInGameRequest;
import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.in_game.response.InGameResponse;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InGameService {

    private final PlayerService playerService;
    private final RoomService roomService;
    private final BossSkillService bossSkillService;

    @Transactional
    public void updateInGameData(Long loginMemberId, PlayerInGameRequest request) {
        if (request.getIsBoss()) {
            bossSkillService.clear();
            Room room = roomService.findEntityByMemberId(loginMemberId);
            bossSkillService.addSkill(room.getId(), request.getSkillUsed());
        } else {
            Hero hero = (Hero) playerService.findByMemberId(loginMemberId);
            hero.update(
                    request.getHp(),
                    request.getEnergy(),
                    request.getPos(),
                    request.getDamageDealt(),
                    request.getMotion()
            );

            Room room = hero.getRoom();

            Optional<Boss> optionalBoss = room.getPlayers().stream()
                    .filter(player -> player instanceof Boss)
                    .findFirst()
                    .map(Boss.class::cast);
            optionalBoss.ifPresent(boss -> boss.decreaseHp(request.getDamageDealt()));
        }
    }

    public InGameResponse getInGameData(Long roomId) {
        RoomDto roomDto = roomService.findDtoById(roomId);

        int numOfPlayers = roomDto.getPlayerDtos().size();
        SkillDto skillUsed = bossSkillService.getSkillUsed(roomId, numOfPlayers);

        return InGameResponse.of(skillUsed, roomDto.getPlayerDtos());
    }
}
