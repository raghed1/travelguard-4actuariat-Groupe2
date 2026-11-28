package esprit.msassurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@EnableDiscoveryClient
@SpringBootApplication


@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8020/api/assurance",
                        description = "API Gateway - Load Balancer"),

                @Server(url = "http://localhost:8081",
                        description = "MS Assurance - Instance 8081"),

                @Server(url = "http://localhost:8022",
                        description = "MS Assurance - Instance 8022")
        }
)

public class MsAssuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAssuranceApplication.class, args);
    }

}
