package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.dto.member.request.SignUpRequest;
import ajou.mse.dimensionguard.dto.member.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    @Operation(
            summary = "회원가입",
            description = "<p>ID, PW를 전달받아 회원가입을 진행합니다."
    )
    @PostMapping("/sign-up")
    public MemberResponse signUp(@RequestBody SignUpRequest request) {
        return null;
    }
}
