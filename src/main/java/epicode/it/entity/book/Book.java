package epicode.it.entity.book;

import epicode.it.entity.publication.Publication;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@NamedQuery(name = "findAll_Book", query = "SELECT a FROM Book a")
@Table(name="books")
public class Book extends Publication {

    private String author;

    private String genre;


}