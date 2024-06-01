package controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class InitialController {

  @GetMapping("/insecure")
  public String insecure() {
    return "Insecure endpoint";
  }

  @GetMapping("/secure")
  public String secure() {
    return "secure endpoint";
  }
}
