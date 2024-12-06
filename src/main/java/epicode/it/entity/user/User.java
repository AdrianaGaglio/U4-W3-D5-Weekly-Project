package epicode.it.entity.user;

import epicode.it.entity.loan.Loan;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@Entity
@NamedQuery(name = "findAll_User", query = "SELECT a FROM User a")
@NamedQuery(name = "findByCardNumberWithLoans", query = "SELECT u, l FROM User u JOIN u.loans l WHERE u.cardNumber = :cardNumber AND l.actualReturnDate IS NULL")
@Table(name="users")
public class User {

    public static String generateRandomCard(int length) {
        // caratteri per generare la stringa
        String alphanumeric = "0123456789";

        Random random = new Random();
        StringBuilder result = new StringBuilder();
        result.append("USC-");

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

    private String name;

    private String surname;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name="card_number")
    private String cardNumber = generateRandomCard(16);

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Loan> loans = new ArrayList<>();
}