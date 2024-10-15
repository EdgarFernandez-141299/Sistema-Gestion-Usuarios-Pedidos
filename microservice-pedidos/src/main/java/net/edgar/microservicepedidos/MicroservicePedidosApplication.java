package net.edgar.microservicepedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MicroservicePedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePedidosApplication.class, args);
	}

}
