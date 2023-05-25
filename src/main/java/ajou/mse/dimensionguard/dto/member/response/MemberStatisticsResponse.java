package ajou.mse.dimensionguard.dto.member.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberStatisticsResponse {

    @Schema(description = "Total number of games played", example = "15")
    private Long numOfGames;

    @Schema(description = "The number of games a member has won as Hero", example = "10")
    private Long numOfBosses;

    @Schema(description = "The number of games a member has won as Hero", example = "3")
    private Long numOfBossWins;

    @Schema(description = "The total number of games the member has played as Hero", example = "5")
    private Long numOfHeroes;

    @Schema(description = "The number of games a member has won as Hero", example = "4")
    private Long numOfHeroWins;
}
