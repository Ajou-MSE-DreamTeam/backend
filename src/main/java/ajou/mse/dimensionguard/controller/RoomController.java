package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.dto.room.response.*;
import ajou.mse.dimensionguard.gameService.GameSyncService;
import ajou.mse.dimensionguard.security.UserPrincipal;
import ajou.mse.dimensionguard.service.MemberService;
import ajou.mse.dimensionguard.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final MemberService memberService;
    private final RoomService roomService;
    private final GameSyncService gameSyncService;

    @Operation(
            summary = "게임 룸 생성",
            description = "<p>게임 룸(대기실)을 생성합니다." +
                    "<p>생성한 호스트는 boss player가 됩니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping
    public ResponseEntity<RoomResponseWithPlayerStatus> create(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        RoomDto roomDto = roomService.createRoom(userPrincipal.getMemberId());

        return ResponseEntity
                .created(URI.create("/api/rooms/" + roomDto.getId()))
                .body(RoomResponseWithPlayerStatus.from(roomDto));
    }

    @Operation(
            summary = "게임 룸 참가",
            description = "<p>게임 룸(대기실)에 참가합니다." +
                    "<p>참가한 플레이어는 hero player가 됩니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomResponseWithPlayerStatus> join(
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
                .body(RoomResponseWithPlayerStatus.from(roomDto));
    }

    @Operation(
            summary = "대기실 목록 조회",
            description = "<p>대기실(게임이 아직 시작되지 않은 게임 룸) 전체 목록을 조회합니다",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping
    public WaitingRoomListResponse searchWaitingRoom() {
        List<RoomCompactResponse> roomList = roomService.findAllByStatusReady().stream()
                .map(roomDto -> {
                    MemberDto host = memberService.findDtoById(roomDto.getCreatedBy());
                    return RoomCompactResponse.from(roomDto, host.getNickname());
                }).toList();
        return new WaitingRoomListResponse(roomList);
    }

    @Operation(
            summary = "게임 시작 여부 확인",
            description = "<p>특정 게임 룸에 대해 게임 시작 여부를 확인합니다." +
                    "<p>게임이 진행중이거나 종료된 경우에는 <code>true</code>를, 게임이 아직 시작되지 않은 상태라면 <code>false</code>를 응답합니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping("/{roomId}/start")

    public CheckGameStartResponse checkGameStarted(
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Integer roomId
    ) {
        return roomService.checkGameStarted(roomId);
    }

    @Operation(
            summary = "게임 준비",
            description = "<p>게임 준비를 수행합니다." +
                    "<p>대기실에 있는 player를 ready 상태로 변경합니다." +
                    "<p>모든 플레이어가 ready 상태가 되었을 때 요청에 대한 응답을 반환합니다." +
                    "<p>API 호출 후 15초 동안 모든 플레이어가 준비되기를 기다립니다.",
            security = @SecurityRequirement(name = "access-token")
    )
    @ApiResponses({
            @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomResponse.class))),
            @ApiResponse(
                    description = "<p>[2501] 서버 내부 오류로 인해 thread sleep이 중단된 경우" +
                            "<p>[2502] 게임 준비 API 호출 후 15초 동안 참가자 전원의 준비가 되지 않은 경우",
                    responseCode = "500", content = @Content
            )
    })
    @PatchMapping("/{roomId}/ready")
    public RoomResponse ready(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Integer roomId
    ) {
        RoomDto roomDto = roomService.ready(userPrincipal.getMemberId(), roomId);

        try {
            gameSyncService.waitUntilEveryoneIsReady(roomId);
        } catch (Exception ex) {
            roomService.init(roomId);
            throw ex;
        }

        return RoomResponse.from(roomDto);
    }
}
