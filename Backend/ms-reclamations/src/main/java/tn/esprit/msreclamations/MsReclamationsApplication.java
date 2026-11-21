package tn.esprit.msreclamations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsReclamationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReclamationsApplication.class, args);
    }

}
