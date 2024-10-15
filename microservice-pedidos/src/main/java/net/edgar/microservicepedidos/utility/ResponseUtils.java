package net.edgar.microservicepedidos.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import net.edgar.microservicepedidos.model.dto.GlobalErrorResponseDTO;
import net.edgar.microservicepedidos.model.dto.GlobalSuccessResponseDTO;
import org.slf4j.MDC;

import java.util.List;

import static net.edgar.microservicepedidos.constant.MicroservicePedidosConstant.ResponseConstant.OK_MENSAJE_BASE;
import static net.edgar.microservicepedidos.constant.MicroservicePedidosConstant.TraceabilityConstant.SPAN_ID_KEY;
import static net.edgar.microservicepedidos.constant.MicroservicePedidosConstant.TraceabilityConstant.TRACE_ID_KEY;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    public static <T> GlobalSuccessResponseDTO<Object> generateSuccessResponse(T respuesta) {
        return GlobalSuccessResponseDTO.builder()
                .mensaje(OK_MENSAJE_BASE)
                .folio(String.format("%s-%s", MDC.get(TRACE_ID_KEY), MDC.get(SPAN_ID_KEY)))
                .respuesta(respuesta)
                .build();
    }

    public static GlobalErrorResponseDTO generateErrorResponse(String codigo, String mensaje, List<String> lstDetalles) {
        return GlobalErrorResponseDTO.builder()
                .codigo(codigo)
                .folio(String.format("%s-%s", MDC.get(TRACE_ID_KEY), MDC.get(SPAN_ID_KEY)))
                .mensaje(mensaje)
                .detalles(lstDetalles)
                .build();
    }

    public static GlobalErrorResponseDTO generateErrorResponse(String codigo, String folio, String mensaje, List<String> lstDetalles) {
        return GlobalErrorResponseDTO.builder()
                .codigo(codigo)
                .folio(folio)
                .mensaje(mensaje)
                .detalles(lstDetalles)
                .build();
    }

}
