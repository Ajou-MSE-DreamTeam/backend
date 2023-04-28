package ajou.mse.dimensionguard.dto.in_game.response;

import ajou.mse.dimensionguard.dto.player.PlayerDto;
import ajou.mse.dimensionguard.dto.player.response.PlayerResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InGameResponse {

    @Schema(description = "보스가 사용한 스킬", example = "10")
    private Integer skillUsed;

    @Schema(description = "게임에 참여중인 플레이어들의 정보")
    private List<PlayerResponse> players;

    public static InGameResponse of(Integer skillUsed, List<PlayerDto> players) {
        return new InGameResponse(
                skillUsed,
                players.stream()
                        .map(PlayerResponse::from)
                        .toList()
        );
    }
}
