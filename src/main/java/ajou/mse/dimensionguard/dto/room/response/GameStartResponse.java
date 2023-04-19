package ajou.mse.dimensionguard.dto.room.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GameStartResponse {

    @Schema(description = "게임 시작 여부", example = "false")
    private Boolean isStarted;

    public static GameStartResponse of(Boolean isStarted) {
        return new GameStartResponse(isStarted);
    }
}
