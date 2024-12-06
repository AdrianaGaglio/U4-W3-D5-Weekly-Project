package epicode.it.utilities;

import epicode.it.entity.book.Book;
import epicode.it.entity.book.dao.BookDAO;
import epicode.it.entity.loan.Loan;
import epicode.it.entity.loan.dao.LoanDAO;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static java.lang.Long.parseLong;

public class Utilities {

    private static final Scanner scanner = new Scanner(System.in);
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    public static EntityManager em = emf.createEntityManager();

    public static PublicationDAO publicationDAO = new PublicationDAO(em);
    public static BookDAO bookDAO = new BookDAO(em);
    public static MagazineDAO magazineDAO = new MagazineDAO(em);
    public static LoanDAO loanDAO = new LoanDAO(em);
    public static UserDAO userDAO = new UserDAO(em);

    // mostra il menu da terminale
    public static int showMainMenu() {
        System.out.println("Select an option to proceed: (0 to exit program):");
        System.out.println("=> 1. Load complete list of publications");
        System.out.println("=> 2. Add a new publication");
        System.out.println("=> 3. Search a publication by ISBN");
        System.out.println("=> 4. Delete a publication");
        System.out.println("=> 5. Search publication by title");
        System.out.println("=> 6. Search publication by year");
        System.out.println("=> 7. Search publication by author");
        System.out.println("=> 8. Show number of active loans by card number");
        System.out.println("=> 9. Search all expired loans and not yet returned");
        System.out.println("=> 10. Update loan status");
        System.out.println("=> 11. Add new loan");
        System.out.println("=> 12. Add new user");
        System.out.println();
        System.out.println("Select an option:");

        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    // mostra l'elenco delle pubblicazioni
    public static void loadArchive() {
        List<Publication> archive = publicationDAO.getAll();
        System.out.println("=> Complete archive list:");
        archive.forEach(System.out::println);
        System.out.println("==================================================");
    }


    // permette di aggiungere una nuova pubblicazione
    public static void addPublication() {
        System.out.println("What do you want to add? 1. Book 2. Magazine (or 0 to cancel)");
        int subOption = scanner.nextInt();
        scanner.nextLine(); // Consuma il ritorno a capo lasciato da nextInt()

        if (subOption == 0) {
            System.out.println("Insertion cancelled. Returning to main menu...");
            return;
        }

        if (subOption == 1) {
            Book book = new Book();

            System.out.println("Enter the ISBN code (or type 'cancel' to abort):");
            String isbn = scanner.nextLine();

            if (isbn.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setISBN(isbn);

            Publication found = publicationDAO.getByISBN(isbn);

            if (found != null) {
                System.out.println("The ISBN code is already in use.");
                System.out.println("Generating random ISBN...");
                isbn = Publication.generateRandomString(10);
            }

            System.out.println("Enter the title of the book (or type 'cancel' to abort):");
            String title = scanner.nextLine();

            if (title.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setTitle(title);

            System.out.println("Enter the author of the book (or type 'cancel' to abort):");
            String author = scanner.nextLine();

            if (author.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setAuthor(author);

            System.out.println("Enter the genre of the book (or type 'cancel' to abort):");
            String genre = scanner.nextLine();

            if (genre.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setGenre(genre);

            System.out.println("Enter the publication year (or type '0' to cancel):");
            int year = scanner.nextInt();
            scanner.nextLine();

            if (year == 0) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setYear(year);

            System.out.println("Enter the publication pages (or type '0' to cancel):");
            int pages = scanner.nextInt();
            scanner.nextLine();

            if (pages == 0) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            book.setPages(pages);

            bookDAO.save(book);

        } else if (subOption == 2) {
            Magazine magazine = new Magazine();
            System.out.println("Enter the ISBN code (or type 'cancel' to abort):");
            String isbn = scanner.nextLine();

            if (isbn.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            magazine.setISBN(isbn);

            Publication found = publicationDAO.getByISBN(isbn);

            if (found != null) {
                System.out.println("The ISBN code is already in use.");
                System.out.println("Generating random ISBN...");
                isbn = Publication.generateRandomString(10);
            }

            System.out.println("Enter the title of the magazine (or type 'cancel' to abort):");
            String title = scanner.nextLine();

            if (title.equalsIgnoreCase("cancel")) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            magazine.setTitle(title);

            while (true) {
                System.out.println("Enter the publication year (or type '0' to cancel):");
                int year = scanner.nextInt();
                scanner.nextLine();

                if (year == 0) {
                    System.out.println("Insertion cancelled. Returning to main menu...");
                    return;
                } else if (year < 1800) {
                    System.out.println("Invalid year. Please try again.");
                } else {
                    magazine.setYear(year);
                    break;
                }
            }

            System.out.println("Enter the publication pages (or type '0' to cancel):");
            int pages = scanner.nextInt();
            scanner.nextLine();

            if (pages == 0) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            magazine.setPages(pages);

            System.out.println("Select periodicity: 1.WEEKLY 2.MONTHLY 3.HALF_YEARLY (or type '0' to cancel):");
            int type = scanner.nextInt();
            scanner.nextLine();

            if (type == 0) {
                System.out.println("Insertion cancelled. Returning to main menu...");
                return;
            }

            Periodicity periodicity = switch (type) {
                case 1 -> Periodicity.WEEKLY;
                case 2 -> Periodicity.MONTHLY;
                case 3 -> Periodicity.HALF_YEARLY;
                default -> null;
            };

            if (periodicity == null) {
                System.out.println("Invalid periodicity. Returning to main menu...");
                return;
            }

            magazine.setPeriodicity(periodicity);

            magazineDAO.save(magazine);

        }
    }

    // permette di ricercare una pubblicazione per ISBN
    public static void searchPublicationByISBN() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Insert the ISBN code for the publication you want to search: (0 to go back to menu)");
            String isbn = scanner.nextLine();
            if (isbn.equals("0")) break;

            Publication p = publicationDAO.getByISBN(isbn);
            if (p != null) {
                System.out.println("Publication found: => " + p);
                valid = true;
                System.out.println("==================================================");
            } else {
                System.out.println("Publication not found, try again.");
            }
        }
    }

    // permette di cancellare una pubblicazione dato un ISBN
    public static void deletePublication() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Insert the ISBN code for the publication you want to delete: (0 to go back to menu)");
            String isbn = scanner.nextLine();
            if (isbn.equals("0")) break;
            Publication found = publicationDAO.getByISBN(isbn);
            if (found != null) {
                publicationDAO.deleteByISBN(found.getISBN());
                System.out.println("Publication deleted successfully!");
                System.out.println(found);
                System.out.println("==================================================");
                valid = true;
            } else {
                System.out.println("Publication not found.");
            }
        }
    }

    // permette di ricercare una pubblicazione per titolo
    public static void searchByTitle() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Insert the title for the publication you want to search: (0 to go back to menu)");
            String title = scanner.nextLine();
            if (title.equals("0")) {
                break;
            } else {
                List<Publication> publications = publicationDAO.getByTitle(title);
                if (publications.size() > 0) {
                    System.out.println("List of publications founded for title: " + title);
                    publications.forEach(System.out::println);
                    System.out.println("==================================================");
                    valid = true;
                } else {
                    System.out.println("No publications found for title: " + title + ", please try again.");
                }
            }
        }
    }

    // permette di ricercare pubblicazioni per anno
    public static void searchPublicationsByYear() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Insert the year for the publication you want to search: (0 to go back to menu)");
            int year = scanner.nextInt();
            scanner.nextLine();
            if (year == 0) {
                break;
            } else {
                List<Publication> publications = publicationDAO.getByYear(year);
                if (publications.size() > 0) {
                    System.out.println("List of publications founded for year: " + year);
                    publications.forEach(System.out::println);
                    System.out.println("==================================================");
                    valid = true;
                } else {
                    System.out.println("No publications found for year: " + year + ", please try again.");
                }
            }
        }
    }

    // permette di ricercare pubblicazioni per autore
    public static void searchPublicationsByAuthor() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Digit author name to search: (0 to go back to menu)");
            String author = scanner.nextLine();
            if (author.equals("0")) break;
            List<Book> publications = bookDAO.getByAuthor(author);
            if (publications.size() > 0) {
                System.out.println("List of publications founded for: " + author);
                publications.forEach(System.out::println);
                System.out.println("==================================================");
                valid = true;
            } else {
                System.out.println("No publications found for author: " + author + ", please try again.");
            }
        }
    }

    // mostra il numero di prestiti attivi per numero di carta
    public static void showNumberOfActiveLoansByCardNumber() {
        boolean valid = false;
        while (!valid) {
            System.out.println("Digit card number to search: (0 to go back to menu)");
            String cardNumber = scanner.nextLine();
            if (cardNumber.equals("0")) break;
            List<Object[]> byCard = userDAO.findLoansByUser(cardNumber);
            if (byCard.size() > 0) {
                System.out.println("List of active loans for card number: " + cardNumber);
                for (int i = 0; i < byCard.size(); i++) {
                    Loan loan = (Loan) byCard.get(i)[1];

                    System.out.println(loan);
                }
                System.out.println("==================================================");
                valid = true;
            } else {
                System.out.println("No active loans found for card number: " + cardNumber + ", please try again.");
            }
        }
    }

    // mostra tutti i prestiti scaduti non ancora restituiti
    public static void searchExpired() {
        LocalDate date = LocalDate.now();
        List<Loan> expiredLoans = loanDAO.findAllExpired(date);
        if (expiredLoans.size() > 0) {
            System.out.println("List of expired loans:");
            for (Loan loan : expiredLoans) {
                System.out.println(loan);
            }
            System.out.println("==================================================");
        } else {
            System.out.println("No expired loans found.");
        }
    }

    // aggiorna prenotazione
    public static void updateLoan() {
        while (true) {
            System.out.println("Insert card number to search: (0 to go back to menu)");
            String cardNumber = scanner.nextLine();
            if (cardNumber.equals("0")) break;
            List<Object[]> byCard = userDAO.findLoansByUser(cardNumber);
            if (byCard.size() > 0) {
                for (int i = 0; i < byCard.size(); i++) {
                    Loan loan = (Loan) byCard.get(i)[1];
                    System.out.println(i + 1 + ". " + loan);
                }
                System.out.println("==================================================");
                System.out.println("Do you want to update a loan status? (y/n)");
                char choice = scanner.next().charAt(0);
                scanner.nextLine();
                if (choice == 'y') {
                    System.out.println("Insert loan id to update: (0 to go back to menu)");
                    Long loanNumber = scanner.nextLong();
                    scanner.nextLine();
                    if (loanNumber == 0) break;
                    Loan found = loanDAO.getById(loanNumber);
                    if (found != null) {
                        found.setActualReturnDate(LocalDate.now());
                        loanDAO.update(found);
                        Publication publication = publicationDAO.getById(found.getPublication().getId());
                        if (publication != null) {
                            publication.setLoan(null);
                            publicationDAO.update(publication);
                            System.out.println("Loan updated successfully!");
                            System.out.println("==================================================");
                            break;
                        }
                    }
                } else {
                    break;
                }
            } else {
                System.out.println("No active loans found for card number: " + cardNumber + ", please try again.");
            }
        }

    }

    public static void addLoan() {
        Loan loan = new Loan();
        loan.setStartDate(LocalDate.now());
        loan.setExpectedReturnDate(LocalDate.now().plusDays(30));
        loan.setActualReturnDate(null);
        System.out.println("Insert card number for the loan:");
        List<Publication> publications = publicationDAO.getAll();
        for (int i = 0; i < publications.size(); i++) {
            System.out.println(i + 1 + ". " + publications.get(i));
        }
        System.out.println("==================================================");
        boolean valid = false;
        while (!valid) {
            System.out.println("Select publication id to add to loan: (0 to go back to menu)");
            Long publicationId = scanner.nextLong();
            scanner.nextLine();
            if (publicationId == 0) break;
            Publication publication = publicationDAO.getById(publicationId);
            if (publication == null) {
                System.out.println("Invalid publication id, please try again.");
            } else {
                if (publication.getLoan() == null) {
                    loan.setPublication(publication);
                    publication.setLoan(loan);
                    while (true) {
                        System.out.println("Insert card number:");
                        String cardNumber = scanner.nextLine();
                        User user = userDAO.getByCardNumber(cardNumber);
                        if (user != null) {
                            loan.setUser(user);
                            user.getLoans().add(loan);
                            userDAO.update(user);
                            loanDAO.save(loan);
                            System.out.println("Loan added successfully!");
                            System.out.println(loan);
                            System.out.println("==================================================");
                            valid = true;
                            break;
                        } else {
                            System.out.println("Invalid card number, please try again.");
                        }
                    }
                } else {
                    System.out.println("Publication is already loaned, please try again.");
                }
            }
        }
    }

    public static void addUser() {
        User user = new User();
        System.out.println("Insert user name:");
        user.setName(scanner.nextLine());
        System.out.println("Insert user surname:");
        user.setSurname(scanner.nextLine());
        System.out.println("Insert user year of birth:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Insert user month of birth:");
        int month = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Insert user day of birth:");
        int day = scanner.nextInt();
        Date birthDate = Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setDateOfBirth(birthDate);
        userDAO.save(user);
        System.out.println("User added successfully!");
        System.out.println(user);
        System.out.println("==================================================");
    }
}
