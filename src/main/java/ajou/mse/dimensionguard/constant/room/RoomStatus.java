package ajou.mse.dimensionguard.constant.room;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "<p>Status of game room." +
                "<p>The following three items exist." +
                "<ul>" +
                "<li><code>READY</code>: Before the game starts, in preparation" +
                "<li><code>IN_PROGRESS</code>: The game is in progress" +
                "<li><code>DONE</code>: The game has ended" +
                "</ul>",
        example = "READY"
)
public enum RoomStatus {

    READY, IN_PROGRESS, DONE
}
