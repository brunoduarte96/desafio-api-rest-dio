package duarte.br.desafioapirestdio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Animes {
    @Id
    private Long id;
    private String title;
    private String studio;
    private String director;
    private String  description;
}
