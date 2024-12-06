package epicode.it.entity.publication;

import epicode.it.entity.loan.Loan;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Random;

@Data
@Entity
@NamedQuery(name = "findAll_Publication", query = "SELECT a FROM Publication a")
@Table(name = "publications")
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class Publication {

    public static String generateRandomString(int length) {
        // caratteri per generare la stringa
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";

        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // Estrai un carattere casuale dalla stringa alfanumerica
            int index = random.nextInt(alphanumeric.length());
            result.append(alphanumeric.charAt(index));
        }
        return result.toString().toUpperCase();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String ISBN = generateRandomString(10);

    private String title;

    private int year;

    private int pages;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private Loan loan;

}