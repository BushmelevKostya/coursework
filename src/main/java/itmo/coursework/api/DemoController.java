package itmo.coursework.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @GetMapping("/one")
    public String demoHello() {
        return "Hello Kostya!";
    }
    @GetMapping("/two")
    @PreAuthorize("hasRole('ADMIN')")
    public String demoAdm() {
        return "Yuppu yo";
    }
}
