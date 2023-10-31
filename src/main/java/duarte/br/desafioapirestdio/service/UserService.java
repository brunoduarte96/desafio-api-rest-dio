package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.model.User;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private AnimesRepository animesRepository;


    public List<User> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<User> getUsuariosById(Long id) {
        return usuarioRepository.findById(id);
    }

    public User createUsuario(User u) {
        return usuarioRepository.save(u);
    }

    public Optional<User> updateUsuario(Long id, User u) {
        Optional<User> usuarioNovo = usuarioRepository.findById(id);
        if (usuarioNovo.isPresent()) {
            User usuario = usuarioNovo.get();
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
    public Optional<User> addAnimeToUsuario(Long usuarioId, String animeTitle) {
        Optional<User> usuarioOptional = usuarioRepository.findById(usuarioId);
        List<Animes> animesEncontrados = animesRepository.findByTitle(animeTitle);

        if (usuarioOptional.isPresent() && !animesEncontrados.isEmpty()) {
            User usuario = usuarioOptional.get();

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
