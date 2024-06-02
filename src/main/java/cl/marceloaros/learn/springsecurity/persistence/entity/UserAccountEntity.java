package cl.marceloaros.learn.springsecurity.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor @Builder
@Getter @Setter
@Entity
@Table(name = "user_account")
public class UserAccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  @Column(name = "is_enabled")
  private boolean isEnabled;

  @Column(name = "is_not_expired")
  private boolean isNotExpired;

  @Column(name = "is_not_locked")
  private boolean isNotLocked;

 @Column(name = "credentials_not_expired")
 private boolean isCredentialsNotExpired;

 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 @JoinTable(name = "useraccount_roles",
   joinColumns = @JoinColumn(name = "useraccount_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
 private Set<RolesEntity> roles = new HashSet<>();

}

