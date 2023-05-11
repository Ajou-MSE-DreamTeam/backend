package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.security.UserPrincipal;
import ajou.mse.dimensionguard.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Player")
@RequiredArgsConstructor
@RequestMapping("/api/players")
@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Operation(
            summary = "Leaving the room",
            description = "<p>Leave the game room you're participating in. " +
                    "<p>Leaving a room means deleting your own player data from the room you're participating in." +
                    "<p>If the user trying to leave is the host of room, will dissolve(delete) the room itself.",
            security = @SecurityRequirement(name = "access-token")
    )
    @DeleteMapping
    public void exit(@Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal) {
        playerService.exit(userPrincipal.getMemberId());
    }
}
