package epicode.it.entity.magazine;

import epicode.it.entity.publication.Publication;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@NamedQuery(name = "findAll_Magazine", query = "SELECT a FROM Magazine a")
@Table(name="magazines")
public class Magazine extends Publication {
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

}