package duarte.br.desafioapirestdio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  username;
    private String  password;
    private String email;
    @ManyToMany
    private List<Animes> animes;

}
