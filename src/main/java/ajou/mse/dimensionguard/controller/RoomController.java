package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.room.RoomDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
