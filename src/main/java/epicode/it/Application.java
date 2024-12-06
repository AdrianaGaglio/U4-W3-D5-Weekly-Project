package epicode.it;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    private static EntityManager em = emf.createEntityManager();

    public static PublicationDAO publicationDAO = new PublicationDAO(em);
    public static BookDAO bookDAO = new BookDAO(em);
    public static MagazineDAO magazineDAO = new MagazineDAO(em);
    public static LoanDAO loanDAO = new LoanDAO(em);
    public static UserDAO userDAO = new UserDAO(em);

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
        System.out.println();
        System.out.println("Select an option:");

        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void loadArchive() {
        List<Publication> archive = publicationDAO.getAll();
        System.out.println("=> Complete archive list:");
        archive.forEach(System.out::println);
        System.out.println("==================================================");
    }


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

    public static void main(String[] args) {

        boolean exit = false;

        while (!exit) {
            try {
                int option = showMainMenu();
                switch (option) {
                    case 1 -> loadArchive();
                    case 2 -> addPublication();
                    case 3 -> searchPublicationByISBN();
                    case 4 -> deletePublication();
                    case 5 -> searchByTitle();
                    case 6 -> searchPublicationsByYear();
                    case 7 -> searchPublicationsByAuthor();
                    case 8 -> showNumberOfActiveLoansByCardNumber();
                    case 0 -> {
                        System.out.println("Exiting program...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
