package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.model.Usuario;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AnimesRepository animesRepository;


    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuariosById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario u) {
        return usuarioRepository.save(u);
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario u) {
        Optional<Usuario> usuarioNovo = usuarioRepository.findById(id);
        if (usuarioNovo.isPresent()) {
            Usuario usuario = usuarioNovo.get();
            usuario.setUsername(u.getUsername());
            usuario.setEmail(u.getEmail());
            usuario.setPassword(u.getPassword());
            return Optional.of(usuarioRepository.save(usuario));
        } else {
            return Optional.empty();
        }

    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
    public Optional<Usuario> addAnimeToUsuario(Long usuarioId, String animeTitle) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        List<Animes> animesEncontrados = animesRepository.findByTitle(animeTitle);

        if (usuarioOptional.isPresent() && !animesEncontrados.isEmpty()) {
            Usuario usuario = usuarioOptional.get();

            // Adicione os animes encontrados à lista de animes do usuário (se já não estiverem lá)
            List<Animes> animesDoUsuario = usuario.getAnimes();
            for (Animes anime : animesEncontrados) {
                if (!animesDoUsuario.contains(anime)) {
                    animesDoUsuario.add(anime);
                }
            }

            // Atualize a lista de animes do usuário
            usuario.setAnimes(animesDoUsuario);

            // Salve o usuário atualizado no banco de dados
            return Optional.of(usuarioRepository.save(usuario));
        }

        return Optional.empty(); // Usuário ou anime não encontrado
    }


}
