package net.edgar.microserviceusuarios.controller.advice;

import lombok.extern.slf4j.Slf4j;

import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.exception.NotFoundException;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.GlobalErrorResponseDTO;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.ResponseConstant.*;
import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.*;
import static net.logstash.logback.argument.StructuredArguments.v;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponseDTO> noSuchElementExceptionHandler(NotFoundException notFoundException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, notFoundException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        NOT_FOUND_CODIGO_BASE,
                        NOT_FOUND_MENSAJE_BASE,
                        Collections.singletonList(notFoundException.getMessage()))
                , NOT_FOUND);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalErrorResponseDTO> MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missingServletRequestParameterException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, missingServletRequestParameterException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(String.format("%s es requerido", missingServletRequestParameterException.getParameterName())))
                , BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalErrorResponseDTO> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException dataIntegrityViolationException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, dataIntegrityViolationException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList("Los datos proporcionados no cumplen con las restricciones requeridas"))
                , BAD_REQUEST);
    }

    @ExceptionHandler(UpdateDatabaseException.class)
    public ResponseEntity<GlobalErrorResponseDTO> UpdateDatabaseExceptionHandler(UpdateDatabaseException updateDatabaseException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, updateDatabaseException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(updateDatabaseException.getMessage()))
                , BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GlobalErrorResponseDTO> badCredentialsExceptionHandler(BadCredentialsException badCredentialsException) {
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        UNAUTHORIZED_CODIGO_BASE,
                        UNAUTHORIZED_MENSAJE_BASE,
                        Collections.singletonList(BAD_CREDENTIALS_DETALLES_MENSAJE_BASE))
                , UNAUTHORIZED);

    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<GlobalErrorResponseDTO> existingUserExceptionHandler(ExistingUserException existingUserException) {
        log.error("{}", v(EXCEPTION_DETAIL_KEY, existingUserException));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        EXISTING_USER_CODIGO_BASE,
                        EXISTING_USER_MENSAJE_BASE,
                        Collections.singletonList(existingUserException.getMessage()))
                , BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, methodArgumentNotValidException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                                .map(FieldError::getDefaultMessage)
                                .toList())
                , BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponseDTO> globalExceptionHandler(Exception exception) {
        log.error("{}", v(EXCEPTION_DETAIL_KEY, exception));

        System.out.println(exception.getClass());
        if (exception instanceof NoResourceFoundException noResourceFoundException) {
            return new ResponseEntity<>(
                    ResponseUtils.generateErrorResponse(
                            NOT_FOUND_CODIGO_BASE,
                            NOT_FOUND_MENSAJE_BASE,
                            Collections.singletonList(String.format("No se encontro el recurso /%s", noResourceFoundException.getResourcePath()))),
                    NOT_FOUND);
        }
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        INTERNAL_SERVER_ERROR_CODIGO_BASE,
                        INTERNAL_SERVER_ERROR_MENSAJE_BASE,
                        Collections.singletonList(exception.getMessage())),
                INTERNAL_SERVER_ERROR);
    }
}
