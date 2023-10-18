package duarte.br.desafioapirestdio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Animes {
    @Id
    private Long id;
    private String title;
    private String studio;
    private String director;
    private String description;
}
