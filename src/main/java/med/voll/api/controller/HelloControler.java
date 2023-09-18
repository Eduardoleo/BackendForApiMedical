package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloControler {

    @GetMapping //mappea el metodo get en la ruta para la aplicacion y qeu nos de un hello world
    public String helloWorld() {
        return "Hello World from Euro ";
    }
}
