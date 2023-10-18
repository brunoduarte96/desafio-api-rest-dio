package duarte.br.desafioapirestdio.repository;

import duarte.br.desafioapirestdio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
