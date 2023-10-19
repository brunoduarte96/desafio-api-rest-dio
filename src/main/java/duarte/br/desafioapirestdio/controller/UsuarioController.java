package duarte.br.desafioapirestdio.controller;

import duarte.br.desafioapirestdio.model.Usuario;
import duarte.br.desafioapirestdio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getByUsuarioId(@PathVariable Long id) {
        Optional<Usuario> usuarios = usuarioService.getUsuariosById(id);
        return usuarios.map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario createusuario = usuarioService.createUsuario(usuario);
        return new ResponseEntity<>(createusuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{usuarioId}/addAnime")
    public ResponseEntity<Usuario> addAnimeToUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Map<String, String> requestBody) {
        String animeTitle = requestBody.get("anime");

        if (animeTitle != null) {
            Optional<Usuario> usuarioOptional = usuarioService.addAnimeToUsuario(usuarioId, animeTitle);

            if (usuarioOptional.isPresent()) {
                return new ResponseEntity<>(usuarioOptional.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Requisição inválida
    }

}






