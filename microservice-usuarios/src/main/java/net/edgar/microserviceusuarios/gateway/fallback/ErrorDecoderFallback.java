package net.edgar.microserviceusuarios.gateway.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.edgar.microserviceusuarios.exception.ExternalServiceResponseFailedException;
import net.edgar.microserviceusuarios.model.dto.GlobalErrorResponseDTO;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.TraceabilityConstant.TRACE_ID_KEY;
import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.TraceabilityConstant.TRACE_ID_SPAN_ID_FORMATO;

@Component
@RequiredArgsConstructor
@Slf4j
public class ErrorDecoderFallback implements ErrorDecoder {

    private final ObjectMapper objectMapper;


    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {

        String jsonErrorString = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        Request request = response.request();
        String requestUrl = request.url();
        int httpCodeResponse = response.status();

        log.error("Request with error -> URL: {}, Method: {}, Request Headers: {}, Response Status: {}, Request Body: {}",
                requestUrl, request.httpMethod(), request.headers(), httpCodeResponse,
                Objects.nonNull(request.body()) ? new String(request.body(), StandardCharsets.UTF_8) : Strings.EMPTY);

        log.error("Server Response Error -> {}", jsonErrorString);

        GlobalErrorResponseDTO globalErrorResponseDTO = objectMapper.readValue(jsonErrorString, GlobalErrorResponseDTO.class);

        return new ExternalServiceResponseFailedException(
                globalErrorResponseDTO.getCodigo(),
                String.format(TRACE_ID_SPAN_ID_FORMATO,
                        MDC.get(TRACE_ID_KEY),
                        Arrays.stream(globalErrorResponseDTO.getFolio().split("-"))
                                .skip(1)
                                .findFirst()
                                .orElse(Strings.EMPTY)),
                httpCodeResponse,
                globalErrorResponseDTO.getMensaje(),
                globalErrorResponseDTO.getDetalles()
        );
    }

}
