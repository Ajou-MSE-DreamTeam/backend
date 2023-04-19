package ajou.mse.dimensionguard.constant.room;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "<p>게임 룸의 상태." +
                "<p>다음 세 가지 항목이 존재한다." +
                "<ul>" +
                "<li>READY: 게임 시작 전, 준비 중" +
                "<li>IN_PROGRESS: 게임 진행 중" +
                "<li>DONE: 게임이 종료된 상태" +
                "</ul>",
        example = "READY"
)
public enum RoomStatus {

    READY, IN_PROGRESS, DONE
}
