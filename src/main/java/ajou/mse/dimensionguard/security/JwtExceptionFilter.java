package ajou.mse.dimensionguard.security;

import ajou.mse.dimensionguard.dto.exception.ErrorResponse;
import ajou.mse.dimensionguard.exception.ExceptionType;
import ajou.mse.dimensionguard.exception.auth.TokenValidateException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenValidateException ex) {
            setErrorResponse(ex.getClass(), response);
        }
    }

    /**
     * Exception 정보를 입력받아 응답할 error response를 설정한다.
     *
     * @param classType Exception의 class type
     * @param response  HttpServletResponse 객체
     */
    private void setErrorResponse(
            Class<? extends Exception> classType,
            HttpServletResponse response
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");

        ExceptionType exceptionType = ExceptionType.from(classType).orElse(ExceptionType.UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(exceptionType.getCode(), exceptionType.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
