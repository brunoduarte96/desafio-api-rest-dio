package duarte.br.desafioapirestdio.repository;

import duarte.br.desafioapirestdio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
