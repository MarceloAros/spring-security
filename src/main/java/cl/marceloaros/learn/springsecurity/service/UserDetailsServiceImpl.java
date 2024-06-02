package cl.marceloaros.learn.springsecurity.service;

import cl.marceloaros.learn.springsecurity.persistence.entity.UserAccountEntity;
import cl.marceloaros.learn.springsecurity.persistence.repository.IUserAccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private IUserAccountRepository userAccountRepository;

  public UserDetailsServiceImpl(IUserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccountEntity userAccountEntity = userAccountRepository.findByUsername(username)
      .orElseThrow(() ->
        new UsernameNotFoundException("The user account for the username '" + username + "' does not exist."));

    List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

    userAccountEntity.getRoles().forEach(role -> authoritiesList.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name())));

    userAccountEntity.getRoles().stream()
      .flatMap(role -> role.getPermissionsList().stream())
      .forEach(permission -> authoritiesList.add(new SimpleGrantedAuthority(permission.getName())));

    return new User(
      userAccountEntity.getUsername(),
      userAccountEntity.getPassword(),
      userAccountEntity.isEnabled(),
      userAccountEntity.isNotExpired(),
      userAccountEntity.isCredentialsNotExpired(),
      userAccountEntity.isNotLocked(),
      authoritiesList);
  }
}
