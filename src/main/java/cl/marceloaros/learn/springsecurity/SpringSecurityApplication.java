package cl.marceloaros.learn.springsecurity;

import cl.marceloaros.learn.springsecurity.persistence.entity.PermissionEntity;
import cl.marceloaros.learn.springsecurity.persistence.entity.RoleEnum;
import cl.marceloaros.learn.springsecurity.persistence.entity.RolesEntity;
import cl.marceloaros.learn.springsecurity.persistence.entity.UserAccountEntity;
import cl.marceloaros.learn.springsecurity.persistence.repository.IUserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityApplication.class, args);
  }

  @Bean
  CommandLineRunner crear(IUserAccountRepository useraccountRepository, PasswordEncoder passwordEncoder) {
    return args -> {

      /* PERMISION */
      PermissionEntity createPermission = PermissionEntity.builder()
        .name("CREATE")
        .build();

      PermissionEntity readPermission = PermissionEntity.builder()
        .name("READ")
        .build();

      PermissionEntity updatePermission = PermissionEntity.builder()
        .name("UPDATE")
        .build();

      PermissionEntity deletePermission = PermissionEntity.builder()
        .name("DELETE")
        .build();

      /* ROLES */
      RolesEntity adminRole = RolesEntity.builder()
        .role(RoleEnum.ADMIN)
        .permissionsList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
        .build();

      RolesEntity userRole = RolesEntity.builder()
        .role(RoleEnum.USER)
        .permissionsList(Set.of(createPermission, readPermission))
        .build();

      RolesEntity guestRole = RolesEntity.builder()
        .role(RoleEnum.GUEST)
        .permissionsList(Set.of(readPermission))
        .build();

      RolesEntity developerRole = RolesEntity.builder()
        .role(RoleEnum.DEVELOPER)
        .permissionsList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
        .build();

      /* USERS*/
      UserAccountEntity userMarcelo = UserAccountEntity.builder()
        .username("marcelo")
        .password(passwordEncoder.encode("123456"))
        .isEnabled(true)
        .isNotExpired(true)
        .isNotLocked(true)
        .isCredentialsNotExpired(true)
        .roles(Set.of(
          adminRole
        ))
        .build();

      UserAccountEntity userAntonio = UserAccountEntity.builder()
        .username("antonio")
        .password(passwordEncoder.encode("123456"))
        .isEnabled(true)
        .isNotExpired(true)
        .isNotLocked(true)
        .isCredentialsNotExpired(true)
        .roles(Set.of(
          userRole
        ))
        .build();

      UserAccountEntity userLizbeth = UserAccountEntity.builder()
        .username("lizbeth")
        .password(passwordEncoder.encode("123456"))
        .isEnabled(true)
        .isNotExpired(true)
        .isNotLocked(true)
        .isCredentialsNotExpired(true)
        .roles(Set.of(
          guestRole
        ))
        .build();

      UserAccountEntity userJose = UserAccountEntity.builder()
        .username("jose")
        .password(passwordEncoder.encode("123456"))
        .isEnabled(true)
        .isNotExpired(true)
        .isNotLocked(true)
        .isCredentialsNotExpired(true)
        .roles(Set.of(
          developerRole
        ))
        .build();

      useraccountRepository.saveAll(List.of(userMarcelo, userAntonio, userLizbeth, userJose));
    };
  }

}
