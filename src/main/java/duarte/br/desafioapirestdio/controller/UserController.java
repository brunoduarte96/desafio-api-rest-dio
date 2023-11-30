package duarte.br.desafioapirestdio.controller;

import duarte.br.desafioapirestdio.dto.UserAnimeDto;
import duarte.br.desafioapirestdio.model.User;
import duarte.br.desafioapirestdio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UserController {

    @Autowired
    private UserService usuarioService;


    @GetMapping
    public ResponseEntity<List<UserAnimeDto>> getAllUsuarios() {
        List<UserAnimeDto> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAnimeDto> getByUsuarioId(@PathVariable Long id) {
        UserAnimeDto user = usuarioService.getUsuariosById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping
    public ResponseEntity<User> createUsuario(@RequestBody User usuario) {
        User createusuario = usuarioService.createUsuario(usuario);
        return new ResponseEntity<>(createusuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsuario(@PathVariable Long id, @RequestBody User usuario) {
        usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{usuarioId}/addAnime")
    public ResponseEntity<User> addAnimeToUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Map<String, String> requestBody) {
        String animeTitle = requestBody.get("anime");

        if (animeTitle != null) {
            Optional<User> usuarioOptional = usuarioService.addAnimeToUsuario(usuarioId, animeTitle);

            if (usuarioOptional.isPresent()) {
                return new ResponseEntity<>(usuarioOptional.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}






