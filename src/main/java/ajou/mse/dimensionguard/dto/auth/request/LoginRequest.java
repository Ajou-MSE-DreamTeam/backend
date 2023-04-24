package ajou.mse.dimensionguard.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest {

    @Schema(description = "ID", example = "test1234")
    @NotEmpty
    private String id;

    @Schema(description = "PW", example = "1234")
    @NotEmpty
    private String password;
}
