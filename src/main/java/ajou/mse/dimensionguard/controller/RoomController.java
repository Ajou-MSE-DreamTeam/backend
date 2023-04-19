package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.player.PlayerDto;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.dto.room.response.GameStartResponse;
import ajou.mse.dimensionguard.dto.room.response.RoomResponse;
import ajou.mse.dimensionguard.security.UserPrincipal;
import ajou.mse.dimensionguard.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "게임 룸")
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    @Operation(
            summary = "게임 룸 생성",
            description = "<p>게임 룸(대기실)을 생성합니다." +
                    "<p>생성한 호스트는 boss player가 됩니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping
    public ResponseEntity<RoomResponse> create(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        RoomDto roomDto = roomService.createRoom(userPrincipal.getMemberId());

        return ResponseEntity
                .created(URI.create("/api/rooms/" + roomDto.getId()))
                .body(RoomResponse.from(roomDto));
    }

    @Operation(
            summary = "게임 룸 참가",
            description = "<p>게임 룸(대기실)에 참가합니다." +
                    "<p>참가한 플레이어는 hero player가 됩니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomResponse> join(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Integer roomId
    ) {
        RoomDto roomDto = roomService.join(userPrincipal.getMemberId(), roomId);
        List<PlayerDto> players = roomDto.getPlayerDtos();

        return ResponseEntity
                .created(URI.create("/api/players/" + players.get(players.size() - 1).getId()))
                .body(RoomResponse.from(roomDto));
    }

    @Operation(
            summary = "게임 시작 여부 확인",
            description = "<p>특정 게임 룸에 대해 게임 시작 여부를 확인합니다." +
                    "<p>게임이 진행중이거나 종료된 경우에는 <code>true</code>를, 게임이 아직 시작되지 않은 상태라면 <code>false</code>를 응답합니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping("/{roomId}/start")
    public GameStartResponse checkGameStarted(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Integer roomId
    ) {
        boolean isStarted = roomService.checkGameStarted(userPrincipal.getMemberId(), roomId);
        return GameStartResponse.of(isStarted);
    }
}
