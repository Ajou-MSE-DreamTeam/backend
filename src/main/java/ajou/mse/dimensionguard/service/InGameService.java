package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.in_game.request.PlayerInGameRequest;
import ajou.mse.dimensionguard.dto.in_game.response.InGameResponse;
import ajou.mse.dimensionguard.dto.player.response.PlayerResponse;
import ajou.mse.dimensionguard.dto.redis.RedisSkillInfo;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.exception.room.NoBossException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InGameService {

    private final PlayerService playerService;
    private final RoomService roomService;
    private final SkillService skillService;

    @Transactional
    public void updateInGameData(Long loginMemberId, PlayerInGameRequest request) {
        if (request.getIsBoss()) {
            updateInGameDataForBoss(loginMemberId, request);
        } else {
            updateInGameDataForHero(loginMemberId, request);
        }
    }

    public InGameResponse getInGameData(Long roomId) {
        RoomDto roomDto = roomService.findDtoById(roomId);
        SkillDto skillUsed = skillService.findDtoById(roomId);
        return new InGameResponse(
                skillUsed,
                roomDto.getPlayerDtos().stream()
                        .map(PlayerResponse::from)
                        .toList()
        );
    }

    private void updateInGameDataForBoss(Long loginMemberId, PlayerInGameRequest request) {
        Room room = roomService.findByMemberId(loginMemberId);
        countNumOfSkillUsed(request.getSkillUsed(), room);
        skillService.addSkill(room.getId(), request.getSkillUsed());
    }

    private void updateInGameDataForHero(Long loginMemberId, PlayerInGameRequest request) {
        Hero hero = (Hero) playerService.findByMemberId(loginMemberId);
        Room room = hero.getRoom();
        Boss boss = getBossFromRoom(room);

        hitToBoss(hero, boss);
        takeDamageToHero(hero, request.getHp(), room.getId(), boss);

        hero.update(
                request.getHp(),
                request.getEnergy(),
                request.getPos(),
                request.getDamageDealt(),
                request.getMotion()
        );
    }

    private Boss getBossFromRoom(Room room) {
        return room.getPlayers().stream()
                .filter(player -> player instanceof Boss)
                .findFirst()
                .map(Boss.class::cast)
                .orElseThrow(NoBossException::new);
    }

    private void countNumOfSkillUsed(SkillDto skill, Room room) {
        if (isSkillUsed(skill)) {
            Boss boss = getBossFromRoom(room);
            boss.increaseNumOfSkillUsed();
        }
    }

    private boolean isSkillUsed(SkillDto skill) {
        return skill.getNum() > 0;
    }

    private void hitToBoss(Hero hero, Boss boss) {
        hero.addTotalDamageDealt(hero.getDamageDealt());
        boss.decreaseHp(hero.getDamageDealt());
    }

    private void takeDamageToHero(Hero hero, Integer newHp, Long roomId, Boss boss) {
        int damageTaken = hero.getHp() - newHp;
        if (damageTaken > 0) {
            hero.addTotalDamageTaken(damageTaken);
            checkSkillHit(roomId, boss);
        }
    }

    private void checkSkillHit(Long roomId, Boss boss) {
        RedisSkillInfo skill = skillService.findById(roomId);
        if (isFirstHit(skill)) {
            skillService.saveToRedis(new RedisSkillInfo(skill.getRoomId(), skill.getNum(), skill.getPos(), true));
            boss.increaseNumOfSkillHit();
        }
    }

    private boolean isFirstHit(RedisSkillInfo skill) {
        return !skill.getIsHit();
    }
}
