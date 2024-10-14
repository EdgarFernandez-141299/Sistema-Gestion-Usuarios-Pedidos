package net.edgar.microserviceusuarios.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceabilityUtils {

    public static String generateTraceIdSpanId() {

        // Generar un nuevo traceId y spanId para el rastreo de cada solicitud
        LocalDateTime fechaActual = LocalDateTime.now();

        String traceIdSpanIdGenerated = String.valueOf(fechaActual.getYear())
                .concat(String.format("%02d", fechaActual.getMonthValue()))
                .concat(String.format("%02d", fechaActual.getDayOfMonth()));

        return String.format("%s%s", traceIdSpanIdGenerated, UUID.randomUUID().toString().replace("-", Strings.EMPTY).substring(0, 20));

    }
}
