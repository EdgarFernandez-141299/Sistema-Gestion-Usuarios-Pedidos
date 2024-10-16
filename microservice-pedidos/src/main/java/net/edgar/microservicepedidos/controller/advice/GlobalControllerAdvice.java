package net.edgar.microservicepedidos.controller.advice;

import lombok.extern.slf4j.Slf4j;
import net.edgar.microservicepedidos.exception.NotFoundException;
import net.edgar.microservicepedidos.exception.UpdateDatabaseException;
import net.edgar.microservicepedidos.model.dto.GlobalErrorResponseDTO;
import net.edgar.microservicepedidos.utility.ResponseUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;


import static net.edgar.microserviceusuarios.constant.MicroservicePedidosConstant.ResponseConstant.*;
import static net.logstash.logback.argument.StructuredArguments.v;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponseDTO> notFoundExceptionHandler(NotFoundException notFoundException) {
        log.error("{}", v(EXCEPTION_DETAIL_KEY, notFoundException));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        NOT_FOUND_CODIGO_BASE,
                        NOT_FOUND_MENSAJE_BASE,
                        Collections.singletonList(notFoundException.getMessage()))
                , NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalErrorResponseDTO> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, httpMessageNotReadableException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(httpMessageNotReadableException.getMessage()))
                , BAD_REQUEST);
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
        log.error("{}", v(EXCEPTION_DETAIL_KEY, updateDatabaseException));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(updateDatabaseException.getMessage()))
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
