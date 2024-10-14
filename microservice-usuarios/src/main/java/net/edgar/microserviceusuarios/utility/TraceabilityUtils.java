package net.edgar.microserviceusuarios.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.UUID;

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

        // Obtener las primeras tres letras del nombre de la aplicación
        String prefix = applicationName.length() >= 3 ? applicationName.substring(0, 3).toUpperCase() : applicationName.toUpperCase();

        // Obtener las letras después del guion medio
        String suffix = Strings.EMPTY;
        if (applicationName.contains("-")) {
            String[] parts = applicationName.split("-");
            if (parts.length > 1) {
                suffix = parts[1].length() >= 3 ? parts[1].substring(0, 3).toUpperCase() : parts[1].toUpperCase();
            }
        }

        // Combinar prefix y suffix
        String spanIdPrefix = prefix + suffix;

        // Generar el resto del spanId
        LocalDateTime fechaActual = LocalDateTime.now();
        String spanIdDateGenerated = String.valueOf(fechaActual.getYear())
                .concat(String.format("%02d", fechaActual.getMonthValue()))
                .concat(String.format("%02d", fechaActual.getDayOfMonth()));

        return String.format("%s%s%s", spanIdPrefix, spanIdDateGenerated, UUID.randomUUID().toString().replace("-", Strings.EMPTY).substring(0, 20));

    }
}
