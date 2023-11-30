package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.dto.UserAnimeDto;
import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.model.User;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.repository.UserRepository;
import duarte.br.desafioapirestdio.service.exceptions.DataBaseException;
import duarte.br.desafioapirestdio.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private AnimesRepository animesRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UserAnimeDto> getAllUsuarios() {
        List<User> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToUserAnimeDto)
                .toList();

    }

    @Transactional(readOnly = true)
    public UserAnimeDto getUsuariosById(Long id) {
        Optional<User> usuarios = usuarioRepository.findById(id);
        return usuarios.map(this::convertToUserAnimeDto).orElse(null);
    }

    @Transactional
    public User createUsuario(User user) {
        return usuarioRepository.save(user);
    }

    @Transactional
    public Optional<User> updateUsuario(Long id, User user) {
        try {
            User usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(user, usuario);
            User UpdatedUser = usuarioRepository.save(usuario);
            return Optional.of(UpdatedUser);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar usuário: " + e.getMessage());
        }

    }


    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha na integridade referencial");
        }
    }

    public Optional<User> addAnimeToUsuario(Long usuarioId, String animeTitle) {
        Optional<User> usuarioOptional = usuarioRepository.findById(usuarioId);
        List<Animes> animesEncontrados = animesRepository.findByTitle(animeTitle);

        if (usuarioOptional.isPresent() && !animesEncontrados.isEmpty()) {
            User usuario = usuarioOptional.get();

            List<Animes> animesDoUsuario = usuario.getAnimes();
            for (Animes anime : animesEncontrados) {
                if (!animesDoUsuario.contains(anime)) {
                    animesDoUsuario.add(anime);
                }
            }

            usuario.setAnimes(animesDoUsuario);
            return Optional.of(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }

    private UserAnimeDto convertToUserAnimeDto(User user) {
        UserAnimeDto userAnimeDto = modelMapper.map(user, UserAnimeDto.class);
        List<String> animeTitles = user.getAnimes().stream()
                .map(Animes::getTitle)
                .toList();
        userAnimeDto.setAnimes(animeTitles);
        return userAnimeDto;
    }

}
