package duarte.br.desafioapirestdio.service;

import duarte.br.desafioapirestdio.dto.AnimesDto;
import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.model.Director;
import duarte.br.desafioapirestdio.model.Studio;

import java.util.HashSet;
import java.util.Set;

public class MockAnimes {
    public static Animes createMockAnimes() {
        Set<Animes> animesset = new HashSet<>();
        Set<Director> directors = new HashSet<>();
        Set<Studio> studios = new HashSet<>();
        Director director = new Director(1L, "oda", animesset);
        directors.add(director);
        Studio studio = new Studio(1L, "Toei Animation", "japan", "1983", animesset);
        studios.add(studio);
        Animes animes = new Animes(1L, "One piece", studios, directors, "Anime pirate", "test");
        animesset.add(animes);
        return animes;
    }

    public static Animes createMockAnimes(String title) {
        Set<Animes> animesset = new HashSet<>();
        Set<Director> directors = new HashSet<>();
        Set<Studio> studios = new HashSet<>();
        Director director = new Director(1L, "oda", animesset);
        directors.add(director);
        Studio studio = new Studio(1L, "Toei Animation", "japan", "1983", animesset);
        studios.add(studio);
        Animes animes = new Animes(1L, "One piece", studios, directors, "Anime pirate", "test");
        animesset.add(animes);
        return animes;
    }

    public static AnimesDto createMockAnimesDto() {
        AnimesDto animesDto = new AnimesDto();
        animesDto.setId(1L);
        animesDto.setTitle("One piece");
        animesDto.setDescription("Anime pirate");
        animesDto.setStudios(createMockAnimes().getStudios());
        animesDto.setDirectors(createMockAnimes().getDirectors());
        animesDto.setImageUrl("http://");

        return animesDto;


    }


}


