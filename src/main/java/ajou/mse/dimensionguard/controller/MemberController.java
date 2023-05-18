package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.member.MemberDto;
import ajou.mse.dimensionguard.dto.member.request.SignUpRequest;
import ajou.mse.dimensionguard.dto.member.response.ExistenceResponse;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import ajou.mse.dimensionguard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "회원(유저)")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "Sign up",
            description = "<p>Receive ID and PW and proceed sign up."
    )
    @ApiResponses({
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(description = "<p>[2000] If the account ID passed is already in use" +
                    "<p>[2002] If the nickname passed is already in use",
                    responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        MemberDto savedMember = memberService.save(request);
        return ResponseEntity
                .created(URI.create("/api/members/" + savedMember.getId()))
                .body(MemberResponse.from(savedMember));
    }

    @Operation(
            summary = "Check for duplicate account IDs.",
            description = "<p>Check if the account id is already in use." +
                    "<p>Returns <code>true</code> if the account id is in use, or <code>false</code> if the account id is not in use."
    )
    @GetMapping("/id/existence")
    public ExistenceResponse checkAccountIdExistence(@RequestParam String id) {
        return new ExistenceResponse(memberService.existsByAccountId(id));
    }

    @Operation(
            summary = "Check for duplicate nickname.",
            description = "<p>Check if the nickname is already in use." +
                    "<p>Returns <code>true</code> if nickname is in use, or <code>false</code> if nickname is not in use."
    )
    @GetMapping("/name/existence")
    public ExistenceResponse checkMemberNameExistence(@RequestParam String name) {
        return new ExistenceResponse(memberService.existsByName(name));
    }
}
