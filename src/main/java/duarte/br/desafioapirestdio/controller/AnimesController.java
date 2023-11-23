package duarte.br.desafioapirestdio.controller;

import duarte.br.desafioapirestdio.dto.AnimesDto;
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
    public ResponseEntity<Page<AnimesDto>> getAnimes(Pageable pageable) {
        Page<AnimesDto> page = animesService.listAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<AnimesDto>> getByAnimeForName(@PathVariable String title) {
        List<AnimesDto> animes = animesService.findByName(title);
        if (!animes.isEmpty()) {
            return new ResponseEntity<>(animes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AnimesDto> createAnimes(@RequestBody AnimesDto animesDto) {
        AnimesDto createanime = animesService.save(animesDto);
        return new ResponseEntity<>(createanime, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimesDto> updateAnime(@PathVariable Long id, @RequestBody AnimesDto animes) {
        animesService.update(id, animes);
        return ResponseEntity.ok(animes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animesService.delete(id);
        return ResponseEntity.ok().build();
    }

}
