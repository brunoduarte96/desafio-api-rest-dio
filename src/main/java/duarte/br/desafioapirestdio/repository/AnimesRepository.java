package duarte.br.desafioapirestdio.repository;

import duarte.br.desafioapirestdio.model.Animes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimesRepository extends JpaRepository<Animes, Long> {
}
