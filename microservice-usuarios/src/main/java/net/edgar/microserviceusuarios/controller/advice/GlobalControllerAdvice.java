package net.edgar.microserviceusuarios.controller.advice;

import lombok.extern.slf4j.Slf4j;

import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.GlobalErrorResponseDTO;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
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
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GlobalErrorResponseDTO> noSuchElementExceptionHandler(NoSuchElementException noSuchElementException) {
       // log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, noSuchElementException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        NOT_FOUND_CODIGO_BASE,
                        NOT_FOUND_MENSAJE_BASE,
                        Collections.singletonList(noSuchElementException.getMessage()))
                , NOT_FOUND);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalErrorResponseDTO> MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missingServletRequestParameterException) {
        //log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, missingServletRequestParameterException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(String.format("%s es requerido", missingServletRequestParameterException.getParameterName())))
                , BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalErrorResponseDTO> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException dataIntegrityViolationException) {
        //log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, dataIntegrityViolationException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList("Los datos proporcionados no cumplen con las restricciones requeridas"))
                , BAD_REQUEST);
    }

    @ExceptionHandler(UpdateDatabaseException.class)
    public ResponseEntity<GlobalErrorResponseDTO> UpdateDatabaseExceptionHandler(UpdateDatabaseException updateDatabaseException) {
       // log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, updateDatabaseException)));
        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        Collections.singletonList(updateDatabaseException.getMessage()))
                , BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
       // log.error(String.valueOf(v(EXCEPTION_DETAIL_KEY, methodArgumentNotValidException)));

        List<String> details = new ArrayList<>();
        methodArgumentNotValidException.getAllErrors().forEach(error -> {
            details.add(error.getDefaultMessage());
        });

        return new ResponseEntity<>(
                ResponseUtils.generateErrorResponse(
                        BAD_REQUEST_CODIGO_BASE,
                        BAD_REQUEST_MENSAJE_BASE,
                        details)
                , BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponseDTO> globalExceptionHandler(Exception exception) {
        //log.error("{}", v(EXCEPTION_DETAIL_KEY, exception));

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
