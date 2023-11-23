package duarte.br.desafioapirestdio.dto;

import duarte.br.desafioapirestdio.model.Animes;
import duarte.br.desafioapirestdio.model.Director;
import duarte.br.desafioapirestdio.model.Studio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimesDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Set<Director> directors;
    private Set<Studio> studios;

    public AnimesDto(Animes entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
        this.directors = entity.getDirectors();
        this.studios = entity.getStudios();
    }
}
