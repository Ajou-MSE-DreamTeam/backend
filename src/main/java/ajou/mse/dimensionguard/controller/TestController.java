package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/test")
@RestController
public class TestController {

    @PostMapping("/hero")
    public HeroDto testHero(@RequestBody HeroDto heroDto) {
        log.info("[{}] heroDto={}", LogUtils.getLogTraceId(), heroDto);
        return heroDto;
    }

    @PostMapping("/boss")
    public BossDto testBoss(@RequestBody BossDto bossDto) {
        log.info("[{}] bossDto={}", LogUtils.getLogTraceId(), bossDto);
        return bossDto;
    }

    private record HeroDto(
            Integer hp,
            Integer energy,
            Position pos,
            Integer damageDealt,
            Integer motion
    ) {
    }

    private record BossDto(
            Integer hp,
            Integer energy,
            SkillDto skillUsed
    ) {
    }

    private record Position(
            Integer x,
            Integer y
    ) {
    }

    private record SkillDto(
            Long id,
            Position pos
    ) {
    }
}
