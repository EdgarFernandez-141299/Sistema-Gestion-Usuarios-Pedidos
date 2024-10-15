package net.edgar.microserviceusuarios.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceabilityUtils {

    public static String generateTraceId() {

        LocalDateTime fechaActual = LocalDateTime.now();

        String traceIdGenerated = String.valueOf(fechaActual.getYear())
                .concat(String.format("%02d", fechaActual.getMonthValue()))
                .concat(String.format("%02d", fechaActual.getDayOfMonth()));

        return String.format("%s%s", traceIdGenerated, UUID.randomUUID().toString().replace("-", Strings.EMPTY).substring(0, 20));

    }

    public static String generateSpanId(String applicationName) {

        // Dividir el nombre de la aplicación en partes separadas por guiones medios
        String[] parts = applicationName.split("-");

        // Usar Stream para mapear y obtener las primeras tres letras de cada parte no vacía
        String spanIdPrefix = Arrays.stream(parts)
                .filter(part -> !part.isEmpty())
                .map(part -> part.substring(0, Math.min(3, part.length())).toUpperCase())
                .collect(Collectors.joining());

        // Generar el resto del spanId
        LocalDateTime fechaActual = LocalDateTime.now();
        String spanIdDateGenerated = String.valueOf(fechaActual.getYear())
                .concat(String.format("%02d", fechaActual.getMonthValue()))
                .concat(String.format("%02d", fechaActual.getDayOfMonth()));

        // Combinar el prefijo, la fecha y el UUID para formar el spanId final
        return String.format("%s%s%s", spanIdPrefix, spanIdDateGenerated, UUID.randomUUID().toString().replace("-", Strings.EMPTY).substring(0, 20));
    }
}
