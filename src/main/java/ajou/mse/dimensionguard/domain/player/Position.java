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

    @Schema(description = "x coordinate", example = "15.22")
    @NotNull
    private Double x;

    @Schema(description = "y coordinate", example = "30.5")
    @NotNull
    private Double y;
}
