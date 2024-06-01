package cl.marceloaros.learn.springsecurity.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@PreAuthorize("denyAll()")
public class AuthenticationController {

  @GetMapping("/unsecured")
  @PreAuthorize("permitAll()")
  public String insecure() {
    return "Unsecured endpoint";
  }

  @GetMapping("/secured-auth-only")
  @PreAuthorize("isAuthenticated()")
  public String securedAuthOnly() {
    return "Secure endpoint by authentication only";
  }

  @GetMapping("/secured-by-create-authority")
  @PreAuthorize("hasAuthority('CREATE')")
  public String securedByCreateAuthority() {
    return "Secure endpoint by CREATE authority only";
  }

  @GetMapping("/secured-by-create-and-read-authority")
  @PreAuthorize("hasAnyAuthority('CREATE', 'READ')")
  public String securedByCreateAndReadAuthority() {
    return "Secure endpoint by CREATE and READ authority only";
  }

  @GetMapping("/omittedSecurity")
  public String omittedSecurity() {
    return "Omitted Security endpoint";
  }



  /* This below still doesn't work and I don't understand why */
  @GetMapping("/secured-by-admin-role")
  @PreAuthorize("permitAll() and hasAnyRole('ADMIN')")
  public String securedByAdminRole() {
    return "Secured endpoint only by ADMIN role";
  }

  @GetMapping("/secured-by-admin-and-user-roles")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public String securedByAdminAndUserRoles() {
    return "Secured endpoint by roles ADMIN and USER";
  }
}
