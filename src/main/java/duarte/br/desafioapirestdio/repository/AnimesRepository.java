package duarte.br.desafioapirestdio.repository;

import duarte.br.desafioapirestdio.model.Animes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimesRepository extends JpaRepository<Animes, Long> {
    List<Animes> findByTitle(String title);
}
