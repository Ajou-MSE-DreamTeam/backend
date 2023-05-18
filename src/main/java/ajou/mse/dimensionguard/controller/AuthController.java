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

import javax.validation.Valid;

@Tag(name = "Authentication (Login, Token)")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login",
            description = "<p>It receives ID, PW and performs login." +
                    "<p>Responds with an access token as the login result." +
                    "<p>Later, when calling the API that requires login permission, " +
                    "you must request the access token with the token type in the <code>Authorization</code> attribute of the header." +
                    "<p>Ex) If the received access token is <code>eyJ.TF9.jrI</code>, you must set the header to <strong><code>Authorization: Bearer eyJ.TF9.jrI</code></strong> before request."
    )
    @ApiResponses({
            @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(description = "[1503] If id is invalid", responseCode = "401", content = @Content),
            @ApiResponse(description = "[1504] If password is invalid", responseCode = "401", content = @Content)
    })
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.getId(), request.getPassword());
    }
}
