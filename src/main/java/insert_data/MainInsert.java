package insert_data;

import com.github.javafaker.Faker;
import epicode.it.entity.book.Book;
import epicode.it.entity.book.dao.BookDAO;
import epicode.it.entity.loan.Loan;
import epicode.it.entity.loan.LoanDAO;
import epicode.it.entity.magazine.Magazine;
import epicode.it.entity.magazine.Periodicity;
import epicode.it.entity.magazine.dao.MagazineDAO;
import epicode.it.entity.publication.Publication;
import epicode.it.entity.publication.dao.PublicationDAO;
import epicode.it.entity.user.User;
import epicode.it.entity.user.dao.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainInsert {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("it"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        PublicationDAO publicationDAO = new PublicationDAO(em);
        BookDAO bookDAO = new BookDAO(em);
        MagazineDAO magazineDAO = new MagazineDAO(em);

        UserDAO userDAO = new UserDAO(em);
        LoanDAO loanDAO = new LoanDAO(em);

        List<Book> books = new ArrayList<>();
        List<Magazine> magazines = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Book book = new Book();
            book.setTitle(faker.book().title());
            book.setAuthor(faker.book().author());
            book.setGenre(faker.book().genre());
            book.setYear(faker.number().numberBetween(1900, 2022));
            book.setPages(faker.number().numberBetween(150, 450));
            books.add(book);

            Magazine magazine = new Magazine();
            magazine.setTitle(faker.lorem().fixedString(5));
            magazine.setPeriodicity(Periodicity.values()[faker.number().numberBetween(0, 2)]);
            magazine.setYear(faker.number().numberBetween(1900, 2022));
            magazine.setPages(faker.number().numberBetween(20, 100));
            magazines.add(magazine);
        }

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName(faker.name().firstName());
            user.setSurname(faker.name().lastName());
            user.setDateOfBirth(faker.date().birthday());
            users.add(user);
        }

        bookDAO.saveAll(books);
        magazineDAO.saveAll(magazines);
        userDAO.saveAll(users);

        List<Loan> loans = new ArrayList<>();
        List<Publication> publications = publicationDAO.getAll();

        for (int i = 0; i < 30; i++) {
            Loan loan = new Loan();
            User user = userDAO.getById(faker.random().nextInt(1, 100).longValue());
            loan.setUser(user);
            userDAO.update(user);
            loan.setStartDate(faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            loan.setExpectedReturnDate(loan.getStartDate().plusDays(30));
            loan.setActualReturnDate(loan.getStartDate().plusDays(faker.random().nextInt(1, 30)));
            while (true) {
                Publication publication = publicationDAO.getById(faker.random().nextInt(1, 100).longValue());
                Loan found = loanDAO.getByPublicationId(publication);
                if (found == null) {
                    loan.setPublication(publication);
                    loanDAO.save(loan);
                    publication.setLoan(loan);
                    publicationDAO.update(publication);
                    break;
                }
            }
        }

    }
}