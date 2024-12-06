package epicode.it.entity.loan;

import epicode.it.entity.publication.Publication;
import epicode.it.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NamedQuery(name = "findAll_Loan", query = "SELECT a FROM Loan a")
@NamedQuery(name = "findByPublicationId", query = "SELECT a FROM Loan a WHERE a.publication = :publication")
@Table(name="loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name="expected_return_date")
    private LocalDate expectedReturnDate;

    @Column(name="actual_return_date")
    private LocalDate actualReturnDate;


}