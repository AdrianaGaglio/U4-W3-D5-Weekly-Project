package epicode.it.entity.book;

import epicode.it.entity.publication.Publication;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@NamedQuery(name = "findAll_Book", query = "SELECT a FROM Book a")
@NamedQuery(name = "findByAuthor", query = "SELECT a FROM Book a WHERE a.author = :author OR a.author LIKE CONCAT('%', :author, '%')")
@NamedQuery(name = "groupByGenre", query = "SELECT a.genre, COUNT(a) FROM Book a GROUP BY a.genre")
@Table(name="books")
public class Book extends Publication {

    private String author;

    private String genre;

    @Override
    public String toString() {
        return "Book{" +
                "id='" + this.getId() + '\'' +
                ", ISBN='" + this.getISBN() + '\'' +
                ", title='" + this.getTitle() + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + this.getYear() + '\'' +
                ", pages='" + this.getPages() + '\'' +
                ", loan='" + this.getLoan() + '\'' +
                '}';
    }
}