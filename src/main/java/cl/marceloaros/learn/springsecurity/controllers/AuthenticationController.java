package cl.marceloaros.learn.springsecurity.controllers;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  @GetMapping("/insecured")
  public String insecure() {
    return "Insecure endpoint";
  }

  @GetMapping("/secured")
  public String secure() {
    return "Secure endpoint";
  }

  @GetMapping("/omittedSecurity")
  public String omittedSecurity() {
    return "Omitted Security endpoint";
  }

}
