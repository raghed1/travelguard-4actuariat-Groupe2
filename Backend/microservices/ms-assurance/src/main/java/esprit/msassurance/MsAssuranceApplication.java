package esprit.msassurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsAssuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAssuranceApplication.class, args);
    }

}
