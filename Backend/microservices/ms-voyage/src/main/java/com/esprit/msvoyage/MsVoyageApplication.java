package com.esprit.msvoyage;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8020/api/voyage",
                        description = "API Gateway - Load Balancer"),

                @Server(url = "http://localhost:8100",
                        description = "MS voyage - Instance 8100"),

                @Server(url = "http://localhost:8101",
                        description = "MS voyage - Instance 8101")
        }
)
public class MsVoyageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsVoyageApplication.class, args);
    }

}