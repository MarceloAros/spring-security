package cl.marceloaros.learn.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable method Security Annotation in the Controllers methods
public class SecurityConfig {


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .csrf(AbstractHttpConfigurer::disable) // or crsf -> crsf.disable()
      .sessionManagement(sessionManagementConfigurer ->
        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .httpBasic(Customizer.withDefaults())
      .authorizeHttpRequests(http -> {
        http.requestMatchers(HttpMethod.GET, "/authentication/insecured").permitAll();
        http.requestMatchers(HttpMethod.GET, "/authentication/secured").authenticated();
        http.anyRequest().authenticated(); // The security of endpoints that do not have defined security depends on this.
      })
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    return daoAuthenticationProvider;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    List<UserDetails> usersDetailsList = new ArrayList<>();

    usersDetailsList.add(User
      .withUsername("marcelo")
      .password("marcelo")
      .roles("ADMIN")
      .authorities("READ", "CREATE")
      .build());

    usersDetailsList.add(User
      .withUsername("antonio")
      .password("antonio")
      .roles("USER")
      .authorities("READ")
      .build());

    return new InMemoryUserDetailsManager(usersDetailsList);
  }

  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance(); //new BCryptPasswordEncoder();
  }
}
