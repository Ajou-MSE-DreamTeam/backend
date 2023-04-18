package ajou.mse.dimensionguard.dto.member.request;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SignUpRequest {

    @Schema(description = "ID", example = "test1234")
    @NotBlank
    private String id;

    @Schema(description = "PW", example = "1234")
    @NotBlank
    private String password;

    public MemberDto toDto() {
        return MemberDto.of(this.getId(), this.getPassword());
    }
}