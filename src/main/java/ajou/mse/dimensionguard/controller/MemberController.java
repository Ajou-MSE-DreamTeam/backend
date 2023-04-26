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

import java.net.URI;

@Tag(name = "회원(유저)")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원가입",
            description = "<p>ID, PW를 전달받아 회원가입을 진행합니다."
    )
    @ApiResponses({
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(description = "<li>[2000] 전달받은 id가 이미 사용중인 계정 id인 경우" +
                    "<li>[2002] 전달받은 닉네임이 이미 사용중인 닉네임인 경우",
                    responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@RequestBody SignUpRequest request) {
        MemberDto savedMember = memberService.save(request);
        return ResponseEntity
                .created(URI.create("/api/members/" + savedMember.getId()))
                .body(MemberResponse.from(savedMember));
    }

    @Operation(
            summary = "계정 id 중복 여부 확인",
            description = "<p>이미 사용중인 id인지 확인합니다." +
                    "<p>사용중인 id라면 <code>true</code>를, 사용중이지 않은 id라면 <code>false</code>를 반환합니다."
    )
    @GetMapping("/id/existence")
    public ExistenceResponse checkAccountIdExistence(@RequestParam String id) {
        return new ExistenceResponse(memberService.existsByAccountId(id));
    }

    @Operation(
            summary = "닉네임 중복 여부 확인",
            description = "<p>이미 사용중인 닉네임인지 확인합니다." +
                    "<p>사용중인 닉네임이라면 <code>true</code>를, 사용중이지 않은 닉네임이라면 <code>false</code>를 반환합니다."
    )
    @GetMapping("/name/existence")
    public ExistenceResponse checkMemberNameExistence(@RequestParam String name) {
        return new ExistenceResponse(memberService.existsByName(name));
    }
}
