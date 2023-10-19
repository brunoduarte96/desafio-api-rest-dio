package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimesService {

    @Autowired
    private AnimesRepository animesRepository;

    public Page<Animes> listAll(Pageable pageable) {
        return animesRepository.findAll(pageable);
    }

    public List<Animes> findByName(String title) {
        return animesRepository.findByTitle(title);
    }

    public Animes save(Animes animes) {
        return animesRepository.save(animes);
    }

    public Optional<Animes> update(Long id, Animes anime) {
        Optional<Animes> animeUpdate = animesRepository.findById(id);

        if (animeUpdate.isPresent()) {
            Animes existingAnime = animeUpdate.get();
            existingAnime.setTitle(anime.getTitle());
            existingAnime.setStudio(anime.getStudio());
            existingAnime.setDirector(anime.getDirector());
            existingAnime.setDescription(anime.getDescription());

            return Optional.of(animesRepository.save(existingAnime));
        } else {
            return Optional.empty();
        }
    }


    public void delete(Long id) {
        animesRepository.deleteById(id);
    }
}
