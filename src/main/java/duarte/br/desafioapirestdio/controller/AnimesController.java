package duarte.br.desafioapirestdio.controller;

import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.service.AnimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("animes")
public class AnimesController {

    @Autowired
    private AnimesService animesService;

    @GetMapping
    public ResponseEntity<Page<Animes>> getAnimes(Pageable pageable) {
        Page<Animes> page = animesService.listAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Animes>> getByAnimeForName(@PathVariable String title) {
        List<Animes> animes = animesService.findByName(title);
        if (!animes.isEmpty()) {
            return new ResponseEntity<>(animes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Animes> createAnimes(@RequestBody Animes animes) {
        Animes createanime = animesService.save(animes);
        return new ResponseEntity<>(createanime, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animes> updateAnime(@PathVariable Long id, @RequestBody Animes animes) {
        animesService.update(id, animes);
        return ResponseEntity.ok(animes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animesService.delete(id);
        return ResponseEntity.ok().build();
    }

}
