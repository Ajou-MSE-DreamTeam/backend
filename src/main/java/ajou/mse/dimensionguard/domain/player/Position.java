package ajou.mse.dimensionguard.domain.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Embeddable
public class Position {

    @Schema(description = "x 좌표", example = "15")
    @NotNull
    private Integer x;

    @Schema(description = "y 좌표", example = "30")
    @NotNull
    private Integer y;
}
