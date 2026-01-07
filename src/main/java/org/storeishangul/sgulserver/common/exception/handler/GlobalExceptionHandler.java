package org.storeishangul.sgulserver.common.exception.handler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.storeishangul.sgulserver.common.dto.response.RestResponse;
import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;
import org.storeishangul.sgulserver.common.exception.CustomException;
import org.storeishangul.sgulserver.domain.leaderboard.domain.exception.LeaderboardAlreadyExistException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResourceFoundException(MethodArgumentNotValidException e) {

        log.warn("NoResourceFoundException...");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Map<String, String>>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {

        Map<String, String> errors = e.getBindingResult()
            .getAllErrors()
            .stream()
            .collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                error ->  Optional.ofNullable(error.getDefaultMessage()).orElse(Strings.EMPTY)
            ));

        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestResponse<String>> handleMethodArgumentNotValidException(
        MethodArgumentTypeMismatchException e) {

        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestResponse<String>> handleIllegalArgumentException(MissingServletRequestParameterException missingServletRequestParameterException) {
        log.info("{}\n{}", missingServletRequestParameterException.getMessage(), missingServletRequestParameterException.getStackTrace());
        return getResponse(HttpStatus.BAD_REQUEST, missingServletRequestParameterException.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestResponse<String>> handleIllegalArgumentException(HttpRequestMethodNotSupportedException e) {
        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<String>> handleIllegalArgumentException(
        HttpMessageNotReadableException e) {
        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(LeaderboardAlreadyExistException.class)
    public ResponseEntity<RestResponse<String>> handleJwtException(LeaderboardAlreadyExistException e) {
        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getErrorResponse(e);
    }

    @ExceptionHandler(CustomException.class)
    public <T> ResponseEntity<RestResponse<T>> handleCustomException(CustomException e) {
        log.info("{}", e.getMessage());
        e.printStackTrace(System.err);
        return getErrorResponse(e);
    }

    /**
     * 위에 해당하는 예외에 해당하지 않을 때 모든 예외를 처리하는 메소드
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> RestResponse<T> handleUnexpectedException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace(System.err);
        return RestResponse.error(ApplicationExceptionType.INTERNAL_SERVER_ERROR);
    }

    private <T> ResponseEntity<RestResponse<T>> getErrorResponse(CustomException customException) {
        return new ResponseEntity<>(
            RestResponse.error(customException.getType()),
            customException.getHttpStatus()
        );
    }

    private <T> ResponseEntity<RestResponse<T>> getResponse(HttpStatus httpStatus, T value) {
        return new ResponseEntity<>(
            new RestResponse<>(httpStatus, value),
            httpStatus
        );
    }
}
