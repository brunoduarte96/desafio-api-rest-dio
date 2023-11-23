package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.dto.AnimesDto;
import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.service.exceptions.DataBaseException;
import duarte.br.desafioapirestdio.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimesService {

    @Autowired
    private AnimesRepository animesRepository;

    @Transactional(readOnly = true)
    public Page<AnimesDto> listAll(Pageable pageable) {
        Page<Animes> animesPage = animesRepository.findAll(pageable);
        return animesPage.map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<AnimesDto> findByName(String title) {
        List<Animes> animes = animesRepository.findByTitle(title);

        return animes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public AnimesDto save(AnimesDto animesDto) {
        Animes entity = new Animes();
        copyDtoToEntity(animesDto, entity);
        entity = animesRepository.save(entity);
        return convertToDto(entity);

    }

    @Transactional
    public AnimesDto update(Long id, AnimesDto animesDto) {
        try {
            Animes entity = animesRepository.getReferenceById(id);
            copyDtoToEntity(animesDto, entity);
            entity = animesRepository.save(entity);
            return new AnimesDto(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)

    public void delete(Long id) {
        if (!animesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            animesRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha na integridade referencial");
        }
    }

    private AnimesDto convertToDto(Animes animes) {
        return new AnimesDto(animes.getId(), animes.getTitle(), animes.getDescription(), animes.getImageUrl(),
                animes.getDirectors(), animes.getStudios());
    }

    private void copyDtoToEntity(AnimesDto dto, Animes entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setStudios(dto.getStudios());
        entity.setDirectors(dto.getDirectors());
    }
}
