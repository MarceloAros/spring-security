package cl.marceloaros.learn.springsecurity.persistence.repository;

import cl.marceloaros.learn.springsecurity.persistence.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
  Optional<UserAccountEntity> findByUsername(String username);
}
