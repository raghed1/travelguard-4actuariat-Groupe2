
package esprit.msassurance.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assurance")
public class AssuranceController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/test")
    public String test() {
        System.out.println("Requete recue par l'instance : " + serverPort);
        return "SUCCESS : API Gateway -> MS Assurance fonctionne ! "
                + "Instance utilisée : Port " + serverPort;
    }
}
