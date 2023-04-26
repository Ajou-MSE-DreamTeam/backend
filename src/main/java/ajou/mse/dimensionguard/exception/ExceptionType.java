package ajou.mse.dimensionguard.exception;

import ajou.mse.dimensionguard.constant.exception.ValidationErrorCode;
import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.auth.AccountIdNotFoundException;
import ajou.mse.dimensionguard.exception.auth.PasswordNotValidException;
import ajou.mse.dimensionguard.exception.auth.TokenValidateException;
import ajou.mse.dimensionguard.exception.member.AccountIdDuplicateException;
import ajou.mse.dimensionguard.exception.member.MemberIdNotFoundException;
import ajou.mse.dimensionguard.exception.member.MemberNameDuplicateException;
import ajou.mse.dimensionguard.exception.player.PlayerNotFoundByMemberAndRoomException;
import ajou.mse.dimensionguard.exception.room.EveryoneNotReadyException;
import ajou.mse.dimensionguard.exception.room.GameStartException;
import ajou.mse.dimensionguard.exception.room.RoomIdNotFoundException;
import ajou.mse.dimensionguard.log.LogUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Error code 목록
 *
 * <ul>
 *     <li>1000 ~ 1999: 일반 예외. 아래 항목에 해당하지 않는 대부분의 예외가 여기에 해당한다</li>
 *     <li>120X: Validation 관련 예외</li>
 *     <li>1210 ~ 1299: 구체적인 Validation content에 대한 exception. 해당 내용은 {@link ValidationErrorCode}, {@link GlobalExceptionHandler} 참고)</li>
 *     <li>13XX: API/Controller 관련 예외</li>
 *     <li>14XX: DB 관련 예외</li>
 *     <li>15XX: 인증 관련 예외</li>
 *     <li>2000 ~ 2499: 회원({@link Member}) 관련 예외</li>
 *     <li>2500 ~ 2999: 게임 룸({@link Room}) 관련 예외</li>
 *     <li>3000 ~ 3399: 플레이어({@link Player}) 관련 예외</li>
 * </ul>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Getter
public enum ExceptionType {

    /**
     * Global/Normal Exception
     */
    UNHANDLED(1000, "알 수 없는 서버 에러가 발생했습니다.", null),

    /**
     * Validation Exception
     *
     * @see ValidationErrorCode
     */
    METHOD_ARGUMENT_NOT_VALID(1200, "요청 데이터가 잘못되었습니다.", MethodArgumentNotValidException.class),
    CONSTRAINT_VIOLATION(1200, "요청 데이터가 잘못되었습니다.", ConstraintViolationException.class),

    /**
     * Spring MVC Default Exception
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(1300, "지원하지 않는 요청 방식입니다.", HttpRequestMethodNotSupportedException.class),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(1301, "지원하지 않는 요청 데이터 타입입니다.", HttpMediaTypeNotSupportedException.class),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(1302, "요청한 데이터 타입으로 응답을 만들어 낼 수 없습니다.", HttpMediaTypeNotAcceptableException.class),
    MISSING_PATH_VARIABLE(1303, "필요로 하는 path variable이 누락 되었습니다.", MissingPathVariableException.class),
    MISSING_SERVLET_REQUEST_PARAMETER(1304, "필요로 하는 request parameter가 누락 되었습니다.", MissingServletRequestParameterException.class),
    MISSING_REQUEST_HEADER(1305, "필요로 하는 request header가 누락 되었습니다.", MissingRequestHeaderException.class),
    SERVLET_REQUEST_BINDING(1306, "복구 불가능한 fatal binding exception이 발생했습니다.", ServletRequestBindingException.class),
    CONVERSION_NOT_SUPPORTED(1307, "Bean property에 대해 적절한 editor 또는 convertor를 찾을 수 없습니다.", ConversionNotSupportedException.class),
    TYPE_MISMATCH(1308, "Bean property를 설정하던 중 type mismatch로 인한 예외가 발생했습니다.", TypeMismatchException.class),
    HTTP_MESSAGE_NOT_READABLE(1309, "읽을 수 없는 요청입니다. 요청 정보가 잘못되지는 않았는지 확인해주세요.", HttpMessageNotReadableException.class),
    HTTP_MESSAGE_NOT_WRITABLE(1310, "응답 데이터를 생성할 수 없습니다.", HttpMessageNotWritableException.class),
    MISSING_SERVLET_REQUEST_PART(1311, "multipart/form-data 형식의 요청 데이터에 대해 일부가 손실되거나 누락되었습니다.", MissingServletRequestPartException.class),
    NO_HANDLER_FOUND(1312, "알 수 없는 에러가 발생했으며, 에러를 처리할 handler를 찾지 못했습니다.", NoHandlerFoundException.class),
    ASYNC_REQUEST_TIMEOUT(1313, "요청에 대한 응답 시간이 초과되었습니다.", AsyncRequestTimeoutException.class),
    BIND(1314, "Request binding에 실패했습니다. 요청 데이터를 확인해주세요.", BindException.class),

    /**
     * 로그인, 인증 관련 예외
     */
    ACCESS_DENIED(1500, "접근이 거부되었습니다.", null),
    UNAUTHORIZED(1501, "유효하지 않은 인증 정보로 인해 인증 과정에서 문제가 발생하였습니다.", null),
    TOKEN_VALIDATE(1502, "유효하지 않은 token입니다. Token 값이 잘못되었거나 만료되어 유효하지 않은 경우로 token 갱신이 필요합니다.", TokenValidateException.class),
    ACCOUNT_ID_NOT_FOUND(1503, "유효하지 않은 ID입니다.", AccountIdNotFoundException.class),
    PASSWORD_NOT_VALID(1504, "유효하지 않은 비밀번호입니다.", PasswordNotValidException.class),

    /**
     * 회원({@link Member}) 관련 예외
     */
    ACCOUNT_ID_DUPLICATE(2000, "이미 사용중인 id입니다.", AccountIdDuplicateException.class),
    MEMBER_ID_NOT_FOUND(2001, "일치하는 회원을 찾을 수 없습니다.", MemberIdNotFoundException.class),
    NICKNAME_DUPLICATE(2002, "이미 사용중인 닉네임입니다.", MemberNameDuplicateException.class),

    /**
     * 게임 룸({@link Room}) 관련 예외
     */
    ROOM_ID_NOT_FOUND(2500, "게임 방을 찾을 수 없습니다. 해산되거나 게임이 시작된 방일 수 있습니다.", RoomIdNotFoundException.class),
    GAME_START(2501, "알 수 없는 이유로 게임을 시작할 수 없습니다.", GameStartException.class),
    EVERYONE_NOT_READY(2502, "참가자들의 준비를 기다렸으나, 참가자 전원의 준비가 되지 않았습니다.", EveryoneNotReadyException.class),

    /**
     * 플레이어({@link Player}) 관련 예외
     */
    PLAYER_NOT_FOUND_BY_MEMBER_AND_ROOM(3000, "플레이어를 찾을 수 없습니다.", PlayerNotFoundByMemberAndRoomException.class)
    ;

    private final Integer code;
    private final String message;
    private final Class<? extends Exception> type;

    public static Optional<ExceptionType> from(Class<? extends Exception> classType) {
        Optional<ExceptionType> exceptionType = Arrays.stream(values())
                .filter(ex -> ex.getType() != null && ex.getType().equals(classType))
                .findFirst();

        if (exceptionType.isEmpty()) {
            log.error("[{}] 정의되지 않은 exception이 발생하였습니다. Type of exception={}", LogUtils.getLogTraceId(), classType);
        }

        return exceptionType;
    }
}
