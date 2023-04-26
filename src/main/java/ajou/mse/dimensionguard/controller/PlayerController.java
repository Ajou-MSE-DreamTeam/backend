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

@Tag(name = "플레이어")
@RequiredArgsConstructor
@RequestMapping("/api/players")
@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Operation(
            summary = "방 나가기",
            description = "<p>참여중인 게임 방에서 나갑니다. " +
                    "<p>방을 나간다는 것은 참여중인 방에서 자신의 player 데이터를 삭제한다는 의미입니다." +
                    "<p>방을 나가려는 유저가 방의 호스트라면 방 자체를 해산(삭제)합니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @DeleteMapping
    public void exit(@Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal) {
        playerService.exit(userPrincipal.getMemberId());
    }
}
