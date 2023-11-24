package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.dto.AnimesDto;
import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.service.exceptions.DataBaseException;
import duarte.br.desafioapirestdio.service.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimesService {

    @Autowired
    private AnimesRepository animesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<AnimesDto> listAll(Pageable pageable) {
        return animesRepository.findAll(pageable)
                .map(animes -> modelMapper.map(animes, AnimesDto.class));
    }

    @Transactional(readOnly = true)
    public List<AnimesDto> findByName(String title) {
        return animesRepository.findByTitle(title).stream()
                .map(animes -> modelMapper.map(animes, AnimesDto.class))
                .toList();
    }

    @Transactional
    public AnimesDto save(AnimesDto animesDto) {
        Animes animes = modelMapper.map(animesDto, Animes.class);
        Animes animesSave = animesRepository.save(animes);
        return modelMapper.map(animesSave, AnimesDto.class);

    }

    @Transactional
    public AnimesDto update(Long id, AnimesDto animesDto) {
        try {
            Animes entity = animesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animes com id " + id + " não encontrado"));
            copyDtoToEntity(animesDto, entity);
            entity = animesRepository.save(entity);
            return modelMapper.map(entity, AnimesDto.class);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("O ID do anime não foi encontrado: " + id);
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

    private void copyDtoToEntity(AnimesDto dto, Animes entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setStudios(dto.getStudios());
        entity.setDirectors(dto.getDirectors());
    }
}
