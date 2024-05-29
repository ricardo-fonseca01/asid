package dev.danvega.danson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/parents/**")
						.uri("http://172.26.46.46:9090")
				)
				.route(r -> r.path("/students/**")
						.uri("http://172.26.46.46:9091")
				)
				.route(r -> r.path("/clubs/**")
						.uri("http://172.26.46.46:9092")
				)
				.route(r -> r.path("/subjects/**")
						.uri("http://172.26.46.46:9093")
				)
				.route(r -> r.path("/saga/**")
						.uri("http://172.26.46.46:9094")
				)
				.build();
	}

}
