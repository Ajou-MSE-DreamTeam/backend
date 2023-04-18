package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.auth.request.LoginRequest;
import ajou.mse.dimensionguard.dto.auth.response.LoginResponse;
import ajou.mse.dimensionguard.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증(로그인, Token 관련)")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "로그인",
            description = "<p>ID, PW를 전달받아 로그인을 수행합니다." +
                    "<p>로그인 결과로 access token을 응답합니다." +
                    "<p>추후 로그인 권한이 필요한 api를 호출할 때, " +
                    "header의 <code>Authorization</code> 속성에 access token을 token type과 함께 담아 요청해야 합니다." +
                    "<p>Ex) 전달받은 access token이 <code>eyJ.TF9.jrI</code>라면 <strong><code>Authorization: Bearer eyJ.TF9.jrI</code></strong>로 header를 설정한 후 요청해야 함."
    )
    @ApiResponses({
            @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(description = "[1503] ID가 유효하지 않은 경우", responseCode = "401", content = @Content),
            @ApiResponse(description = "[1504] 비밀번호가 유효하지 않은 경우", responseCode = "401", content = @Content)
    })
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getId(), request.getPassword());
    }
}
