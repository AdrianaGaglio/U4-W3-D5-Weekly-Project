package epicode.it;

import epicode.it.utilities.Utilities;
import jdk.jshell.execution.Util;

import java.util.Scanner;

public class Application {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean exit = false;

        while (!exit) {
            try {
                int option = Utilities.showMainMenu();
                switch (option) {
                    case 1 -> Utilities.loadArchive();
                    case 2 -> Utilities.addPublication();
                    case 3 -> Utilities.searchPublicationByISBN();
                    case 4 -> Utilities.deletePublication();
                    case 5 -> Utilities.searchByTitle();
                    case 6 -> Utilities.searchPublicationsByYear();
                    case 7 -> Utilities.searchPublicationsByAuthor();
                    case 8 -> Utilities.showNumberOfActiveLoansByCardNumber();
                    case 9 -> Utilities.searchExpired();
                    case 10 -> Utilities.updateLoan();
                    case 11 -> Utilities.addLoan();
                    case 12 -> Utilities.addUser();
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
        Utilities.em.close();
    }
}
