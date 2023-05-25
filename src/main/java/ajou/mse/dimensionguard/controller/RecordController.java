package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.record.RecordDto;
import ajou.mse.dimensionguard.dto.record.response.RecordResponse;
import ajou.mse.dimensionguard.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Record & Statistics")
@RequiredArgsConstructor
@RequestMapping("/api/record")
@RestController
public class RecordController {

    private final RecordService recordService;

    @Operation(
            summary = "Find game record by game room ID.",
            description = "<p>Get a record of games that have ended by game room ID(<code>roomId</code>).",
            security = @SecurityRequirement(name = "access-token")
    )
    @GetMapping
    public RecordResponse findByRoomId(@RequestParam Long roomId) {
        RecordDto recordDto = recordService.findDtoByRoomId(roomId);
        return RecordResponse.from(recordDto);
    }
}
