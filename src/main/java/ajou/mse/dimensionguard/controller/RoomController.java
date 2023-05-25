package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import ajou.mse.dimensionguard.dto.player.PlayerDto;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.dto.room.response.*;
import ajou.mse.dimensionguard.service.GameSyncService;
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
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Room")
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    private final MemberService memberService;
    private final RoomService roomService;
    private final GameSyncService gameSyncService;

    @Operation(
            summary = "Create game room",
            description = "<p>Create a game room(waiting room)." +
                    "<p>The host who created the room will be a boss player.",
            security = @SecurityRequirement(name = "access-token")
    )
    @ApiResponses({
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = RoomResponseWithPlayerStatus.class))),
            @ApiResponse(description = "[2503] If you are already joining another room and need to retry after leaving the room you are joining", responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RoomResponseWithPlayerStatus> create(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        RoomDto roomDto = roomService.createRoom(userPrincipal.getMemberId());

        return ResponseEntity
                .created(URI.create("/api/rooms/" + roomDto.getId()))
                .body(RoomResponseWithPlayerStatus.from(roomDto));
    }

    @Operation(
            summary = "Join game room",
            description = "<p>Join a game room(waiting room)." +
                    "<p>The participating player becomes the hero player.",
            security = @SecurityRequirement(name = "access-token")
    )
    @ApiResponses({
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = RoomResponseWithPlayerStatus.class))),
            @ApiResponse(description = "[2503] If you are already joining another room and need to retry after leaving the room you are joining", responseCode = "409", content = @Content)
    })
    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomResponseWithPlayerStatus> join(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Long roomId
    ) {
        RoomDto roomDto = roomService.join(userPrincipal.getMemberId(), roomId);
        List<PlayerDto> players = roomDto.getPlayerDtos();

        return ResponseEntity
                .created(URI.create("/api/players/" + players.get(players.size() - 1).getId()))
                .body(RoomResponseWithPlayerStatus.from(roomDto));
    }

    @Operation(
            summary = "Get waiting room list",
            description = "<p>Get the list of waiting rooms(game rooms where games haven't started yet).",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping
    public WaitingRoomListResponse searchWaitingRoom() {
        List<RoomCompactResponse> roomList = roomService.findAllDtosByStatusReady().stream()
                .map(roomDto -> {
                    MemberDto host = memberService.findDtoById(roomDto.getCreatedBy());
                    return RoomCompactResponse.from(roomDto, host.getName());
                }).toList();
        return new WaitingRoomListResponse(roomList);
    }

    @Operation(
            summary = "Check if the game has started",
            description = "<p>Check if a game has started for a specific game room." +
                    "<p>Return <code>true</code> if the game is in progress or has ended, or <code>false</code> if the game has not yet started.",
            security = @SecurityRequirement(name = "access-token")
    )
    @ApiResponses({
            @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = CheckGameStartResponse.class))),
            @ApiResponse(description = "[2500] The room cannot be found because the host has left the room, the room is dissolved, etc.", responseCode = "404", content = @Content)
    })
    @GetMapping("/{roomId}/start")
    public CheckGameStartResponse checkGameStarted(
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Long roomId
    ) {
        return roomService.checkGameStarted(roomId);
    }

    @Operation(
            summary = "Ready",
            description = "<p>Perform ready." +
                    "<p>Change my player in the waiting room to the ready state." +
                    "<p>Returns a response to the request when all players are ready." +
                    "<p>Wait up to 15 seconds after the API call for all players to be ready.",
            security = @SecurityRequirement(name = "access-token")
    )
    @ApiResponses({
            @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomResponse.class))),
            @ApiResponse(
                    description = "<p>[2501] If thread sleep is interrupted due to an internal server error." +
                            "<p>[2502] If all participants are not ready for 15 seconds after the API call",
                    responseCode = "500", content = @Content
            )
    })
    @PatchMapping("/{roomId}/ready")
    public RoomResponse ready(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Parameter(
                    description = "PK of room",
                    example = "1"
            ) @PathVariable Long roomId
    ) {
        roomService.ready(userPrincipal.getMemberId(), roomId);

        try {
            gameSyncService.waitUntilEveryoneIsReady(roomId);
            roomService.setGameStartedAt(roomId, LocalDateTime.now());
        } catch (Exception ex) {
            roomService.init(roomId);
            throw ex;
        }

        RoomDto roomDto = roomService.findDtoById(roomId);
        return RoomResponse.from(roomDto);
    }
}
