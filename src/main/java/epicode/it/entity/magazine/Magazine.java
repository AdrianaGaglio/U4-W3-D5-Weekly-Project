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

    @Override
    public String toString() {
        return "Magazine{" +
                "id='" + this.getId() + '\'' +
                ", ISBN='" + this.getISBN() + '\'' +
                ", title='" + this.getTitle() + '\'' +
                ", periodicity=" + periodicity +
                ", year='" + this.getYear() + '\'' +
                ", pages='" + this.getPages() + '\'' +
                ", loan='" + this.getLoan() + '\'' +
                '}';
    }
}