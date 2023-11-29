package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.dto.AnimesDto;
import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.repository.AnimesRepository;
import duarte.br.desafioapirestdio.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnimesServiceTest {
    private static final Logger log = Logger.getLogger(AnimesServiceTest.class.getName());

    @Mock
    private AnimesRepository animesRepository;

    @Mock
    private ModelMapper modelMapper;
    @Autowired
    @InjectMocks
    private AnimesService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    @DisplayName("should list all anime")
    void listAllAnimes() {
        Animes mockAnimes = MockAnimes.createMockAnimes();

        when(animesRepository.findAll(Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(mockAnimes)));

        Page<AnimesDto> result = service.listAll(Pageable.unpaged());

        assertEquals(1, result.getContent().size(), "The list is empty");
        log.info("The test passed successfully! The list is full");
    }

    @Test
    @DisplayName("should return an empty list when no anime is found")
    void listAllAnimesEmptyList() {

        when(animesRepository.findAll(Pageable.unpaged())).thenReturn(Page.empty());

        Page<AnimesDto> result = service.listAll(Pageable.unpaged());
        assertTrue(result.isEmpty(), "The List should be empty");
        log.info("The Test passed successfully! The list is empty as expected");

    }


    @Test
    @DisplayName("The title of the anime should return")
    void findByNameAnime() {
        String title = "One piece";
        Animes mockAnimes = MockAnimes.createMockAnimes(title);

        when(animesRepository.findByTitle(title)).thenReturn(List.of(mockAnimes));
        List<Animes> result = animesRepository.findByTitle(title);

        assertEquals(1, result.size());
        log.info("The Test passed successfully! the title was found as expected");

    }

    @Test
    @DisplayName("The title of the anime not be found")
    void findByNameAnimeNotFound() {
        String noTitle = "noTitle";

        when(animesRepository.findByTitle(noTitle)).thenReturn(null);

        List<Animes> result = animesRepository.findByTitle(noTitle);
        assertNull(result, "the title should not be found");
        log.info("The Test passed successfully! the title not was found as expected");
    }

    @Test
    @DisplayName("An anime should be saved")
    void saveAnime() {
        Animes mockAnimes = MockAnimes.createMockAnimes();
        AnimesDto animesDto = MockAnimes.createMockAnimesDto();

        Animes savedEntity = new Animes();
        savedEntity.setId(1L);
        savedEntity.setTitle("One piece");
        savedEntity.setDescription("Anime pirate");
        savedEntity.setStudios(mockAnimes.getStudios());
        savedEntity.setDirectors(mockAnimes.getDirectors());
        savedEntity.setImageUrl("http://");

        when(modelMapper.map(animesDto, Animes.class)).thenReturn(savedEntity);
        when(animesRepository.save(savedEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, AnimesDto.class)).thenReturn(animesDto);

        AnimesDto result = service.save(animesDto);
        assertEquals(animesDto.getTitle(), result.getTitle());
        log.info("The test passed successfully! the anime has been saved");;

    }

    @Test
    @DisplayName("An anime shouldn't be saved")
    void noSaveAnime() {
        AnimesDto dto = MockAnimes.createMockAnimesDto();
        Animes mockAnimes = MockAnimes.createMockAnimes();
        Animes savedEntity = new Animes();
        savedEntity.setId(1L);
        savedEntity.setTitle("One piece");
        savedEntity.setDescription("Anime pirate");
        savedEntity.setStudios(mockAnimes.getStudios());
        savedEntity.setDirectors(mockAnimes.getDirectors());
        savedEntity.setImageUrl("http://");
        when(modelMapper.map(dto, Animes.class)).thenReturn(savedEntity);
       when(animesRepository.save(any())).thenThrow(new DataAccessException("Error message") {
       });
       assertThrows(DataAccessException.class,() -> service.save(dto));
       verify(animesRepository,times(1)).save(any());
        log.info("The test passed successfully! the anime was not saved");
    }

    @Test
    @DisplayName("An anime should be update")
    void updateAnime() {
        Animes mockAnimes = MockAnimes.createMockAnimes();
        AnimesDto animesDto = MockAnimes.createMockAnimesDto();

        Animes udpateEntity = new Animes();
        udpateEntity.setId(1L);
        udpateEntity.setTitle("One piece");
        udpateEntity.setDescription("Anime pirate");
        udpateEntity.setStudios(mockAnimes.getStudios());
        udpateEntity.setDirectors(mockAnimes.getDirectors());
        udpateEntity.setImageUrl("http://");

        when(animesRepository.findById(udpateEntity.getId())).thenReturn(Optional.of(udpateEntity));
        when(modelMapper.map(animesDto, Animes.class)).thenReturn(udpateEntity);
        when(animesRepository.save(udpateEntity)).thenReturn(udpateEntity);
        when(modelMapper.map(udpateEntity, AnimesDto.class)).thenReturn(animesDto);
        AnimesDto result = service.update(1L, animesDto);
        assertEquals(animesDto.getTitle(), result.getTitle());
        assertEquals(animesDto.getDescription(), result.getDescription());
        log.info("The test passed successfully! the anime has been update");;
    }
    @Test
    @DisplayName("An anime shouldn't be update")
    void noUpdateAnime() {
      Long id = 1L;
      AnimesDto animesDto = new AnimesDto();
      when(animesRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

      assertThrows(ResourceNotFoundException.class,() -> service.update(id, animesDto));

      verify(animesRepository,never()).save(any());
        log.info("The test passed successfully! the anime was not updated");

    }

    @Test
    @DisplayName("should delete the anime from the list")
    void deleteAnime() {
        long animeId = 1L;
        when(animesRepository.existsById(animeId)).thenReturn(true);

        service.delete(animeId);

        verify(animesRepository, times(1)).deleteById(animeId);
    }
}

